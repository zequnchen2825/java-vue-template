package com.woc.service.service.user.impl;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woc.common.common.RespContants;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.common.shiro.ShiroManager;
import com.woc.common.common.utils.Md5Util;
import com.woc.common.entity.*;
import com.woc.common.entity.extend.WocUserExtend;
import com.woc.common.vo.PageVO;
import com.woc.dao.dao.*;
import com.woc.dao.dao.extend.TUserDaoExtend;
import com.woc.dao.dao.extend.WocUserDaoExtend;
import com.woc.service.service.login.ChannelService;
import com.woc.service.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private TUserDao tUserDao;
    @Autowired
    private TUserDaoExtend tUserDaoExtend;
    @Autowired
    private WocUserDaoExtend wocUserDaoExtend;
    @Autowired
    private WocUserRoleDao wocUserRoleDao;
    @Autowired
    private WocRoleDao wocRoleDao;
    @Autowired
    private ChannelService channelService;

    @Override
    public TUser saveOrUpdate(TUser tUser) throws Exception {
        TUser user = getUserByOpenIdAndChannelId(tUser.getChannelId(), tUser.getOpenId());
        if(user == null){
            tUser.setLastLoginTime(tUser.getCreateTime());
            tUserDao.insert(tUser);
            return tUser;
        }
        user.setSex(tUser.getSex());
        user.setNickName(tUser.getNickName());
        user.setHeadimgUrl(tUser.getHeadimgUrl());
        user.setCountry(tUser.getCountry());
        user.setCity(tUser.getCity());
        user.setProvince(tUser.getProvince());
        user.setLastLoginTime(new Date());

        tUserDao.updateByPrimaryKey(user);
        return user;
    }

    @Override
    public void alarmCourse() {
        LocalDateTime localDateTime = LocalDateTime.now();
        int hour = localDateTime.getHour();
        log.info("整点课程提醒任务执行，当前整点数：{}时",hour);
        List<TUser> tUsers = tUserDaoExtend.findUserAlarmCourseByTime(hour+":00","schedule");


    }

    @Override
    public PageVO getUserList(Integer page, Integer pageSize, String platformTag, String account) throws Exception {

        if(ShiroManager.getIsAdmin() == null){
            throw new BusinessException(RespContants.USER_ISNOT_ADMIN);
        }
        PageHelper.startPage(page, pageSize);
        List<WocUserExtend> wocUsers = wocUserDaoExtend.selectByPlatformTag(platformTag, account);

        return new PageVO<WocUserExtend>(wocUsers,new PageInfo(wocUsers).getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUser(WocUser wocUser,Integer roleId) throws Exception {

        WocUserExample wocUserExample = new WocUserExample();
        wocUserExample.createCriteria().andAccountEqualTo(wocUser.getAccount()).andIsDeleteEqualTo(0);
        List<WocUser> wocUsers = wocUserDaoExtend.selectByExample(wocUserExample);
        if(!wocUsers.isEmpty()){
            throw new BusinessException(RespContants.USER_IS_EXIST);
        }
        WocRole wocRole = wocRoleDao.selectByPrimaryKey(roleId);

        if(wocRole == null){
            throw new BusinessException(RespContants.ROLE_NAME_IS_NOT_EXIST);
        }

        wocUser.setSalt(UUID.randomUUID().toString().replace("-",""));
        wocUser.setPassword(Md5Util.md5(wocUser.getPassword(),wocUser.getSalt()));
        wocUser.setCreateTime(new Date());
        wocUser.setUpdateTime(wocUser.getCreateTime());
        wocUser.setIsDelete(0);
        wocUserDaoExtend.insert(wocUser);
        WocUserRole wocUserRole = new WocUserRole();
        wocUserRole.setCreateTime(new Date());
        wocUserRole.setIsDelete(0);
        wocUserRole.setRoleId(roleId);
        wocUserRole.setUserId(wocUser.getId());

        return wocUserRoleDao.insert(wocUserRole)>0;
    }

    @Override
    public boolean updateUser(WocUser wocUser) {
        wocUser.setUpdateTime(new Date());
        wocUser.setPassword(null);
        wocUser.setSalt(null);
        return wocUserDaoExtend.updateByPrimaryKeySelective(wocUser)>0;
    }

    @Override
    public boolean updateUserPwd(WocUser wocUser,String newPwd) throws Exception {
        WocUser wocUserReal = wocUserDaoExtend.selectByPrimaryKey(wocUser.getId());
        if(!wocUser.getPassword().equals(wocUserReal.getPassword())){
            throw new BusinessException(RespContants.PLATFORM_ISNOT_EXIST);
        }
        wocUserReal.setPassword(Md5Util.md5(newPwd,wocUserReal.getSalt()));
        wocUserReal.setUpdateTime(new Date());

        return wocUserDaoExtend.updateByPrimaryKey(wocUserReal)>0;
    }

    @Override
    public List<WocRole> getUserRoleListByUserId(Integer userId) {
        return wocUserDaoExtend.findUserRoleList(userId);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean updateUserRole(List<Integer> addList, List<Integer> removeList,Integer userId) throws Exception {

        for(Integer roleId : addList){
            WocUserRole wocUserRole = new WocUserRole();
            wocUserRole.setUserId(userId);
            wocUserRole.setRoleId(roleId);
            wocUserRole.setIsDelete(0);
            wocUserRole.setCreateTime(new Date());
            if(wocUserRoleDao.insert(wocUserRole) <1){
                throw new BusinessException(RespContants.NET_ERRER);
            }
        }
        for(Integer roleId : removeList){
            WocUserRole wocUserRole = new WocUserRole();
            WocUserRoleExample wocUserRoleExample = new WocUserRoleExample();
            wocUserRoleExample.createCriteria().andRoleIdEqualTo(roleId).andUserIdEqualTo(userId);
            wocUserRole.setRoleId(roleId);
            wocUserRole.setUserId(userId);
            wocUserRole.setIsDelete(0);
            if (wocUserRoleDao.updateByExample(wocUserRole,wocUserRoleExample) <1){
                throw new BusinessException(RespContants.NET_ERRER);
            }
        }
        return true;
    }

    @Override
    public TUser getUserByOpenIdAndChannelId(Integer channnelId, String openid) throws Exception{
        return tUserDaoExtend.getUserByOpenIdAndChannelId(channnelId,openid);
    }

    @Override
    public TUser insertXcxUser(WxMaUserInfo userInfo,WocChannel wocChannel) throws Exception {
        TUser tUser = new TUser();
        tUser.setChannelId(wocChannel.getId());
        tUser.setPlatformId(wocChannel.getPlatformId());
        tUser.setCity(userInfo.getCity());
        tUser.setCountry(userInfo.getCountry());
        tUser.setCreateTime(new Date());
        tUser.setHeadimgUrl(userInfo.getAvatarUrl());
        tUser.setLastLoginTime(tUser.getCreateTime());
        tUser.setNickName(userInfo.getNickName());
        tUser.setOpenId(userInfo.getOpenId());
        tUser.setProvince(userInfo.getProvince());
        tUser.setSex(Integer.parseInt(userInfo.getGender()));

        TUser user = getUserByOpenIdAndChannelId(wocChannel.getId(), userInfo.getOpenId());


        if (user == null) {
            tUserDao.insert(tUser);
        } else{
            tUserDao.updateByPrimaryKeySelective(tUser);
        }


        return tUser;

    }

    @Override
    public TUser getUserByIdAndChannelId(Integer channelId, Integer userId) {
        return tUserDaoExtend.getUserByIdAndChannelId(channelId,userId);
    }
}
