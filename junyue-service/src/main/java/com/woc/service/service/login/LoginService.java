package com.woc.service.service.login;


import com.woc.common.common.bean.RespResult;
import com.woc.common.entity.TUser;
import com.woc.common.vo.TUserVO;

import javax.servlet.http.HttpServletRequest;


public interface LoginService {


    String toLogin(String platform, String channel, String business,String uuid,Integer channelId) throws Exception;

    String toIndex(String platform, String channel, String business, String code, HttpServletRequest request,String uuid,Integer channelId) throws Exception;

    RespResult toXcxLogin(String platform, String channel, String business,String code,HttpServletRequest request,String schoolTag) throws Exception;

    RespResult toXcxUserLogin(String platform, String channel, String business, String encryptedData,String iv,String code,HttpServletRequest request)  throws Exception;

    TUserVO getUserInfo(TUser tUser);
}
