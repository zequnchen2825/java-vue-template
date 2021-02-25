package com.woc.service.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.woc.common.common.Contants;
import com.woc.common.common.RespContants;
import com.woc.common.common.config.BaseConfig;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.common.shiro.ShiroManager;
import com.woc.common.common.shiro.UserToken;
import com.woc.common.entity.WocMenu;
import com.woc.common.entity.WocRole;
import com.woc.common.entity.WocUser;
import com.woc.common.entity.extend.WocMenuExtend;
import com.woc.common.vo.LoginVO;
import com.woc.common.vo.MenuVO;
import com.woc.dao.dao.extend.WocMenuDaoExtend;
import com.woc.dao.dao.extend.WocRoleDaoExtend;
import com.woc.service.service.user.ICmsLoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class CmsLoginServiceImpl implements ICmsLoginService {
    @Autowired
    private BaseConfig baseConfig;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private WocRoleDaoExtend wocRoleDaoExtend;
    @Autowired
    private WocMenuDaoExtend wocMenuDaoExtend;

    public boolean login(WocUser wocUser) throws Exception {
        Subject currentUser = SecurityUtils.getSubject();

        UserToken userToken = new UserToken(wocUser.getAccount(),wocUser.getPassword());

        currentUser.login(userToken);
        currentUser.hasRole("admin");

        return true;

    }

    @Override
    public Boolean logout() {

        Subject currentUser = SecurityUtils.getSubject();

        currentUser.logout();

        return true;
    }

    @Override
    public LoginVO getInfo() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        WocUser loginUser = (WocUser) currentUser.getPrincipal();
        if (loginUser == null) {
            throw new BusinessException(RespContants.USERINFO_ERROR);
        }
        loginUser.setPassword(null);
        loginUser.setSalt(null);


        LoginVO loginVO = new LoginVO();
        loginVO.setUser(loginUser);

        //查找所有的角色
        List<WocRole> wocRoles =  wocRoleDaoExtend.findRolesByUserId(loginUser.getId());
        loginVO.setRoles(wocRoles);
        //判断保存是否为管理员
        if(!wocRoles.isEmpty() && wocRoles.get(0).getSort() == 1 && "admin".equals(wocRoles.get(0).getRoleCode())){
            session.setAttribute(Contants.ADMIN_TAG, true);
        }else{
            session.setAttribute(Contants.ADMIN_TAG,false);
        }

        //保存角色列表
        session.setAttribute(Contants.ROLES_TAG,wocRoles);

        StringBuffer roleIds = new StringBuffer();
        Set<String> roles = new HashSet<>();
        for(WocRole wocRole : wocRoles){
            roleIds.append(wocRole.getId()+",");
            roles.add(wocRole.getRoleCode());
        }
        //查找所有的菜单及权限

        List<WocMenuExtend> wocMenus = null;
        List<WocMenuExtend> premissionMenu = null;

        if(roleIds.length() >0){
            //查找主菜单
            wocMenus = wocMenuDaoExtend.findMenuByRolesIdAndLevel(roleIds.substring(0,roleIds.length()-1),1,0);
            //查找权限
            premissionMenu = wocMenuDaoExtend.findMenuByRolesIdAndLevel(roleIds.substring(0,roleIds.length()-1), 3, null);

        }else{
            wocMenus = new ArrayList<>();
            premissionMenu = new ArrayList<>();
        }


        List<WocMenuExtend> wocMenuExtends = new ArrayList<>();
        //查询子菜单
        for(WocMenu wocMenu : wocMenus){
            WocMenuExtend wocMenuExtend = new WocMenuExtend();
            BeanUtils.copyProperties(wocMenu,wocMenuExtend);
            wocMenuExtend.setSubWocMenu(wocMenuDaoExtend.findMenuByRolesIdAndLevel(roleIds.substring(0,roleIds.length()-1),2,wocMenu.getId()));
            wocMenuExtends.add(wocMenuExtend);
        }
        //保存菜单列表
        session.setAttribute(Contants.MENUS_TAG,wocMenuExtends);
        //权限去重
        Set<String> premissions = new HashSet<>();
        for(WocMenu wocMenu : premissionMenu){
            premissions.add(wocMenu.getPermissionCode());
        }

        //保存权限列表
        session.setAttribute(Contants.PREMISSIONS_TAG,new ArrayList<>(premissions));
        loginVO.setPermissions(new ArrayList<>(premissions));
        loginVO.setMenus(wocMenuExtends);
        loginVO.setChannels(ShiroManager.getChannels());
        loginVO.setPlatform(ShiroManager.getPlatform());
        return loginVO;
    }



}
