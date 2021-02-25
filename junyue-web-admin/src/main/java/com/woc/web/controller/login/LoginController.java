package com.woc.web.controller.login;

import com.woc.common.common.Contants;
import com.woc.common.common.RespContants;
import com.woc.common.common.bean.RespResult;
import com.woc.common.common.utils.VerifyParamUtils;
import com.woc.common.entity.TUser;
import com.woc.service.service.login.LoginService;
import com.woc.web.annotation.IgnoreLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import weixin.popular.bean.wxa.WxaUserInfo;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @IgnoreLogin
    @RequestMapping("/{platform}/{channel}/{business}/tologin")
    public String toLogin(@PathVariable String platform,@PathVariable String channel,
                          @PathVariable String business,String uuid,Integer channelId) throws Exception {

        VerifyParamUtils.verifyIsNull(platform,channel,business);
        return loginService.toLogin(platform,channel,business,uuid, channelId);
    }

    @IgnoreLogin
    @RequestMapping("/{platform}/{channel}/{business}/index")
    public String toIndex(@PathVariable String platform, @PathVariable String channel,
                          @PathVariable String business, String code, String uuid,Integer channelId,HttpServletRequest request) throws Exception {
        VerifyParamUtils.verifyIsNull(platform,channel,business);
        return loginService.toIndex(platform,channel,business,code,request,uuid,channelId);
    }
    @IgnoreLogin
    @RequestMapping("/{platform}/{channel}/{business}/toXcxlogin")
    @ResponseBody
    public RespResult toXcxLogin(@PathVariable String platform,
                                 @PathVariable String channel,
                                 @PathVariable String business,
                                 String code,String schoolTag,HttpServletRequest request) throws Exception {
        VerifyParamUtils.verifyIsNull(platform,channel,business,code);
        return loginService.toXcxLogin(platform,channel,business,code,request,schoolTag);
    }
    @IgnoreLogin
    @RequestMapping("/{platform}/{channel}/{business}/toXcxUserLogin")
    @ResponseBody
    public RespResult toXcxUserLogin(@PathVariable String platform, @PathVariable String channel, @PathVariable String business, String encryptedData,String iv,String code,HttpServletRequest request) throws Exception{
        VerifyParamUtils.verifyIsNull(platform,channel,business,code,iv,encryptedData);
        return loginService.toXcxUserLogin(platform,channel,business,encryptedData,iv,code,request);
    }








}
