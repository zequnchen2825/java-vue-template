package com.woc.web.controller.api;

import com.woc.common.common.bean.RespResult;
import com.woc.service.service.media.MediaService;
import com.woc.web.annotation.IgnoreLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @IgnoreLogin
    @ResponseBody
    @RequestMapping("/{platformTag}/{channelTag}/{groupTag}/getMedia")
    public RespResult getMedia(@PathVariable("platformTag") String platformTag,
                               @PathVariable("groupTag") String groupTag,
                               @PathVariable("channelTag") String channelTag){
        return new RespResult(mediaService.getMedia(platformTag,groupTag,channelTag));
    }

}
