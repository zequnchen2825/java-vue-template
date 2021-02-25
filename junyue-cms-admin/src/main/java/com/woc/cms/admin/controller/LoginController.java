package com.woc.cms.admin.controller;

import com.woc.common.common.RespContants;
import com.woc.common.common.bean.RespResult;
import com.woc.common.entity.WocUser;

import com.woc.common.vo.LoginVO;
import com.woc.dao.dao.TUserMapper;
import com.woc.service.service.user.ICmsLoginService;
import com.woc.cms.admin.annotation.CmsIgnoreLogin;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/login")
@Controller
public class LoginController {


    @Autowired
    private ICmsLoginService iLoginService;
    @Autowired
    private TUserMapper tUserMapper;

    @RequestMapping("/login")
    @ResponseBody
    @CmsIgnoreLogin
    public RespResult index( WocUser wocUser) throws Exception {

//        if(StringUtils.isEmpty(platform)){
//            return new RespResult(RespContants.PLATFORM_TAG_IS_NULL);
//        }
        if(StringUtils.isEmpty(wocUser.getAccount())){
            return new RespResult(RespContants.USER_ACCOUNT_IS_NULL);
        }
        if(StringUtils.isEmpty(wocUser.getPassword())){
            return new RespResult(RespContants.USER_PASSWORD_IS_NULL);
        }

        return new RespResult(iLoginService.login(wocUser));
    }
    @RequestMapping("/getInfo")
    @ResponseBody
    public RespResult getInfo(){
        LoginVO info = iLoginService.getInfo();
        RespResult respResult = new RespResult(info);
        return respResult;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public RespResult logout(){
        return new RespResult(iLoginService.logout());
    }

//    public RespResult test(){
//        QueryWrapper<TUser> tUserQueryWrapper = new QueryWrapper<>();
//        List<com.woc.common.entity.TUser> tUsers = tUserMapper.selectList(tUserQueryWrapper);
//        return R
//    }
}
