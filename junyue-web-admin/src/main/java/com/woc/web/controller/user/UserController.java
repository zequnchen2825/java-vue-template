package com.woc.web.controller.user;

import com.woc.common.common.Contants;
import com.woc.common.common.bean.RespResult;
import com.woc.common.common.utils.VerifyParamUtils;
import com.woc.common.entity.TUser;
import com.woc.common.vo.TUserVO;
import com.woc.service.service.login.LoginService;
import com.woc.service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public RespResult getUserInfo(@RequestAttribute(Contants.USER) TUser tUser){
        TUserVO userInfo = loginService.getUserInfo(tUser);
        return new RespResult(userInfo);
    }

}
