package com.woc.web.controller.common;

import com.woc.common.common.bean.RespResult;
import com.woc.common.common.config.BaseConfig;
import com.woc.common.common.utils.RedisUtils;
import com.woc.service.service.common.CommonService;
import com.woc.service.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/common")
@Controller
public class CommonController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private BaseConfig baseConfig;
    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("/changeJc")
    @ResponseBody
    public RespResult changeJc(){
       return commonService.changeJc();
    }

//    @IgnoreLogin
//    @RequestMapping("/index")
//    public String index(HttpSession session){
//        session.setAttribute("name","陈泽群");
//        return "index";
//    }
//    @RequestMapping("/index")
//    public String index(Model model, String reason,
//                        @RequestAttribute TUser tUser ,
//                        @RequestAttribute WocBusiness wocBusiness,
//                        @RequestAttribute TSchool school){
//        long incr = redisUtils.getIncr(Contants.LOGIN_ERROR + tUser.getOpenId());
//        model.addAttribute("display","none");
//        if(incr > 5){
//            model.addAttribute("isYzm","<input class=\"uk-width-1-1 uk-form-large\" id=\"verifyCode\" style=\"width: 50%;float: left\" type=\"text\" placeholder=\"验证码\">\n" +
//                    "<img style=\"float: right;width: 40%\" src=\"https://chenzequn.mynatapp.cc/woc-web-admin/subLogin/"+school.getSchoolTag()+"/getYzm\">");
//            model.addAttribute("display","display");
//        }
////        model.addAttribute("result",reason);
//        model.addAttribute("reason",reason);
//        model.addAttribute("tologin",baseConfig.getBaseUrl()+"woc-web-admin/subLogin/"+school.getSchoolTag()+"/tologin");
//
//        model.addAttribute("redirect",wocBusiness.getRedirectUrl());
//        model.addAttribute("schoolName",school.getSchoolName());
//        model.addAttribute("schoolMotto",school.getSchoolMotto());
//        model.addAttribute("headUrl",tUser.getHeadimgUrl());
//
//        return "index";
//    }
}
