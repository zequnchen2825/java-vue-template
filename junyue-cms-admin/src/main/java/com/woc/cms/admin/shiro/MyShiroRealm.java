package com.woc.cms.admin.shiro;


import com.alibaba.fastjson.JSONObject;
import com.woc.common.common.Contants;
import com.woc.common.common.RespContants;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.common.exception.ShiroExtException;
import com.woc.common.common.shiro.ShiroManager;
import com.woc.common.common.shiro.UserToken;
import com.woc.common.common.utils.Md5Util;
import com.woc.common.entity.*;
import com.woc.common.entity.extend.WocMenuExtend;
import com.woc.dao.dao.WocChannelDao;
import com.woc.dao.dao.WocPlatformDao;
import com.woc.dao.dao.WocUserDao;
import com.woc.dao.dao.extend.WocMenuDaoExtend;
import com.woc.dao.dao.extend.WocRoleDaoExtend;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private WocUserDao wocUserDao;

    @Autowired
    private WocPlatformDao wocPlatformDao;

    @Autowired
    private WocRoleDaoExtend wocRoleDaoExtend;
    @Autowired
    private WocMenuDaoExtend wocMenuDaoExtend;

    @Autowired
    private WocChannelDao wocChannelDao;


    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info = null;
        try {
            Session session = SecurityUtils.getSubject().getSession();
            WocPlatform platform = ShiroManager.getPlatform();
            WocChannelExample wocChannelExample = new WocChannelExample();
            wocChannelExample.createCriteria().andPlatformIdEqualTo(platform.getId()).andIsDeleteEqualTo(0).andStatusEqualTo(1);
            List<WocChannel> wocChannels = wocChannelDao.selectByExample(wocChannelExample);
            //保存渠道列表
            session.setAttribute(Contants.CHANNELS_TAG,wocChannels);


            info = new SimpleAuthorizationInfo();
//            info.setRoles(roles);
//            info.setStringPermissions(premissions);
        }  catch (Exception e){
            e.printStackTrace();
        }

        return info;
    }

    //验证登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UserToken user = (UserToken)authenticationToken;

        WocUserExample wocUserExample = new WocUserExample();
        wocUserExample.createCriteria().andAccountEqualTo(user.getUsername()).andIsDeleteEqualTo(0);
        List<WocUser> wocUsers = wocUserDao.selectByExample(wocUserExample);

        if(wocUsers.isEmpty()){
            throw new ShiroExtException(RespContants.USER_ISNOT_EXIST);
        }
        if(wocUsers.size() > 1){
            throw new ShiroExtException(RespContants.USER_EXIST_NOT_ONLY);
        }
        WocUser wocUser = wocUsers.get(0);

        Session session = SecurityUtils.getSubject().getSession();

        //查询平台
        WocPlatform wocPlatform = wocPlatformDao.selectByPrimaryKey(wocUser.getPlatformId());
        if(wocPlatform==null){
            throw new ShiroExtException(RespContants.PLATFORM_ISNOT_EXIST);
        }
        if(wocPlatform.getStatus() == 0){
            throw new ShiroExtException(RespContants.PLATFORM_NOT_OPEN);
        }

        //校验密码
        try {
            if(!Md5Util.verify(new String(user.getPassword()),wocUser.getSalt(),wocUser.getPassword())){
                throw new ShiroExtException(RespContants.PASSWORD_ERROR);
            }
        } catch (ShiroExtException e){
            throw new ShiroExtException(e.getCode(),e.getMsg());
        }catch (Exception e) {
            e.printStackTrace();
        }
        user.setPassword(wocUser.getPassword().toCharArray());
        SimpleAuthenticationInfo info =new SimpleAuthenticationInfo(wocUser,wocUser.getPassword(),getName());

        session.setAttribute(Contants.USER_TAG, wocUser);

        session.setAttribute(Contants.PLATFORM_TAG, wocPlatform);


        return info;
    }
}
