package com.woc.web.controller.api;

import com.woc.common.common.bean.RespResult;
import com.woc.common.entity.WocPlatform;
import com.woc.service.service.login.PlatformService;
import com.woc.web.annotation.IgnoreLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/platform")
public class PlatformController {

    @Autowired
    private PlatformService platformService;

    @IgnoreLogin
    @RequestMapping("/{platformTag}")
    @ResponseBody
    public RespResult getPlatformInfo(@PathVariable("platformTag") String platformTag) throws Exception {
        return new RespResult(platformService.selectPlatformByTag(platformTag));
    }
}
