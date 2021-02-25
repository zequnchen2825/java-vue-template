package com.woc.service.service.user;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.github.pagehelper.PageInfo;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.entity.*;
import com.woc.common.vo.PageVO;
import com.woc.common.vo.TUserVO;
import com.woc.common.vo.UserVO;

import java.util.List;

public interface UserService {
    TUser saveOrUpdate(com.woc.common.entity.TUser tUser) throws Exception;

    void alarmCourse();

    PageVO getUserList(Integer page, Integer pageSize, String platformTag, String account) throws Exception;

    boolean saveUser(WocUser wocUser,Integer roleId) throws Exception;

    boolean updateUser(WocUser wocUser);

    boolean updateUserPwd(WocUser wocUser,String newPwd) throws BusinessException, Exception;

    List<WocRole> getUserRoleListByUserId(Integer userId);

    boolean updateUserRole(List<Integer> addList, List<Integer> removeList,Integer userId) throws BusinessException, Exception;

    TUser getUserByOpenIdAndChannelId(Integer id, String openid) throws Exception;

    TUser insertXcxUser(WxMaUserInfo userInfo, WocChannel wocChannel) throws Exception;

    TUser getUserByIdAndChannelId(Integer channelId, Integer userId) throws Exception;
}
