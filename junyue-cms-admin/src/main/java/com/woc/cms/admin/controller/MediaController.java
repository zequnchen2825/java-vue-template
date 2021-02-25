package com.woc.cms.admin.controller;

import com.woc.common.common.bean.PRespResult;
import com.woc.common.common.bean.RespResult;
import com.woc.common.entity.MediaGroup;
import com.woc.common.entity.WocMedia;
import com.woc.common.entity.extend.DO.MediaGroupExtendDO;
import com.woc.common.entity.extend.DO.WocMediaExtendDO;
import com.woc.common.entity.extend.PageParams;
import com.woc.service.service.media.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @RequestMapping("/group/pageList")
    @ResponseBody
    public PRespResult groupPageList(PageParams pageParams, MediaGroupExtendDO mediaGroup){
        return mediaService.groupPageList(pageParams,mediaGroup);
    }
    @RequestMapping("/group/save")
    @ResponseBody
    public RespResult saveGroup(MediaGroup mediaGroup) throws Exception{
        return new RespResult(mediaService.saveGroup(mediaGroup));
    }
    @RequestMapping("/group/update")
    @ResponseBody
    public RespResult updataGroup(MediaGroup mediaGroup) throws Exception{
        return new RespResult(mediaService.updataGroup(mediaGroup));
    }
    @RequestMapping("/group/findGroupById")
    @ResponseBody
    public RespResult findGroupById(Integer id) throws Exception{
        return new RespResult(mediaService.findGroupById(id));
    }

    @RequestMapping("/pageList")
    @ResponseBody
    public PRespResult pageList(PageParams pageParams, WocMediaExtendDO wocMedia){
        return mediaService.pageList(pageParams,wocMedia);
    }
    @RequestMapping("/save")
    @ResponseBody
    public RespResult save(WocMedia media) throws Exception{
        return new RespResult(mediaService.save(media));
    }
    @RequestMapping("/update")
    @ResponseBody
    public RespResult updata(WocMedia media) throws Exception{
        return new RespResult(mediaService.updata(media));
    }
    @RequestMapping("/findById")
    @ResponseBody
    public RespResult findById(Integer id) throws Exception{
        return new RespResult(mediaService.findById(id));
    }


}
