package com.woc.cms.admin.controller;

import com.woc.common.common.bean.PRespResult;
import com.woc.common.common.bean.RespResult;
import com.woc.common.entity.WocPlatform;
import com.woc.service.service.login.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/platform")
public class PlatformController {

    @Autowired
    private PlatformService platformService;

    @RequestMapping("/pagelist")
    @ResponseBody
    public PRespResult pagelist(Integer page,
                                Integer limit){

        PRespResult result = platformService.pagelist(page, limit);
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public RespResult update(@RequestBody WocPlatform wocPlatform){
        return new RespResult(platformService.update(wocPlatform));
    }

    @RequestMapping("/add")
    @ResponseBody
    public RespResult add(@RequestBody WocPlatform wocPlatform) {
        return new RespResult(platformService.insert(wocPlatform));
    }

}
