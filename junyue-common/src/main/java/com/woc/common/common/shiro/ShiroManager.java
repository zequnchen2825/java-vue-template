package com.woc.common.common.shiro;



import com.woc.common.common.Contants;
import com.woc.common.common.utils.StringUtil;
import com.woc.common.entity.*;
import com.woc.common.entity.extend.WocMenuExtend;
import com.woc.common.vo.MenuVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moye on 2017/11/20.
 */
public class ShiroManager {


    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 已认证通过的用户。不包含已记住的用户，这是与user标签的区别所在。与notAuthenticated搭配使用
     *
     * @return 通过身份验证：true，否则false
     */
    public static boolean isAuthenticated() {
        return getSubject() != null && getSubject().isAuthenticated();
    }


    /**
     * 获取当前Session中的用户
     * @return
     */
    public static WocUser getSessionUser(){

        Subject subject = SecurityUtils.getSubject();
        if(subject != null){
            Object object = subject.getPrincipal();
            if(object != null){
                WocUser wocUser = (WocUser) object;
                return wocUser;
            }
        }
        return null;
    }

//    /**
//     * 获取当前用户所有角色
//     * @return
//     */
//    public static List<WocRole> getRoles(){
//        return (List<WocRole>)getSessionAttribute(Contants.ROLES_TAG);
//    }

//    /**
//     * 获取当前用户所有权限
//     * @return
//     */
//    public static List<String> getPermissions(){
//        return (List<String>)getSessionAttribute(Contants.PREMISSIONS_TAG);
//    }
//    /**
//     * 获取当前用户所有菜单
//     * @return
//     */
//    public static List<WocMenuExtend> getMenus(){
//        return (List<WocMenuExtend>)getSessionAttribute(Contants.MENUS_TAG);
//    }
//    /**
//     * 获取当前用户所有菜单
//     * @return
//     */
//    public static List<MenuVO> getMenusList(){
//        List<WocMenuExtend> wocMenuExtends = (List<WocMenuExtend>)getSessionAttribute(Contants.MENUS_TAG);
//        List<MenuVO> menuVOS = new ArrayList<>();
//        dealMenu(wocMenuExtends,menuVOS);
//        return menuVOS;
//    }
//    private static void dealMenu(List<WocMenuExtend> wocMenuExtends, List<MenuVO> menuVOS){
//        for(WocMenuExtend wocMenuExtend :wocMenuExtends){
//
//            if(StringUtils.isNotEmpty(wocMenuExtend.getComponent())){
//                MenuVO menuVO = new MenuVO();
//                menuVO.setComponent(wocMenuExtend.getComponent());
//                menuVO.setName(wocMenuExtend.getMenuName());
//                menuVO.setPath(wocMenuExtend.getMenuUrl());
//                menuVOS.add(menuVO);
//            }else{
//                if(wocMenuExtend.getSubWocMenu() != null){
//                    dealMenu(wocMenuExtend.getSubWocMenu(),menuVOS);
//                }
//            }
//
//        }
//    }
    /**
     * 获取当前用户所在
     * @return
     */
    public static WocPlatform getPlatform(){
        return (WocPlatform)getSessionAttribute(Contants.PLATFORM_TAG);
    }
    /**
     * 获取当前用户平台下所有渠道
     * @return
     */
    public static List<WocChannel> getChannels(){
        return (List<WocChannel>)getSessionAttribute(Contants.CHANNELS_TAG);
    }
    /**
     * 获取当前用户平台下所有渠道
     * @return
     */
    public static Boolean getIsAdmin(){
        return (Boolean)getSessionAttribute(Contants.ADMIN_TAG);
    }


    /**
     * 获取当前用户ID
     * @return
     */
    public static Integer getSessionUid(){

        WocUser wocUser = getSessionUser();

        if(wocUser != null){

            return wocUser.getId();
        }

        return null;
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }


}
