package com.woc.cms.admin.controller;

import com.woc.common.common.bean.PRespResult;
import com.woc.common.common.bean.RespResult;
import com.woc.common.common.shiro.ShiroManager;
import com.woc.common.entity.WocChannel;
import com.woc.common.entity.WocChannelExample;
import com.woc.common.entity.WocPlatform;
import com.woc.dao.dao.WocChannelDao;
import com.woc.service.service.login.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/channel")
public class  ChannelController {

    @Autowired
    private ChannelService channelService;
    @Autowired
    private WocChannelDao wocChannelDao;

    @RequestMapping("/pagelist")
    @ResponseBody
    public PRespResult pagelist(Integer platformId, Integer page,
                                Integer limit) throws Exception {
        WocChannelExample wocChannelExample = new WocChannelExample();
        wocChannelExample.createCriteria().andPlatformIdEqualTo(platformId).andIsDeleteEqualTo(0);
        long count = wocChannelDao.countByExample(wocChannelExample);
        wocChannelExample.setLimit(limit);
        wocChannelExample.setOffset((page-1)*limit.longValue());
        List<WocChannel> wocChannels = wocChannelDao.selectByExample(wocChannelExample);

        return  new PRespResult(wocChannels, (int)count);
    }

    @RequestMapping("/add")
    @ResponseBody
    public RespResult add (@RequestBody WocChannel wocChannel) {
        return  new RespResult(channelService.insert(wocChannel));
    }

    @RequestMapping("/update")
    @ResponseBody
    public RespResult update (@RequestBody WocChannel wocChannel) {
        return  new RespResult(channelService.update(wocChannel));
    }
}
