package com.woc.woctimer.weixin;


import cn.binarywang.wx.miniapp.api.WxMaService;
import com.alibaba.fastjson.JSONObject;
import com.woc.common.common.Contants;
import com.woc.common.common.config.SystemConfig;
import com.woc.common.common.utils.HttpUtils;
import com.woc.common.common.utils.RedisUtils;
import com.woc.common.entity.WocChannel;
import com.woc.common.vo.enumVO.ChannelType;
import com.woc.service.common.WeixinConfig;
import com.woc.service.service.login.ChannelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccessToken {
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private WeixinConfig weixinConfig;
    //没两个小时刷新accessToken
//    @Scheduled(cron = "0 0 0/1 * * ?")
    @Scheduled(initialDelay = 3000 , fixedDelay = 7200000)
    private boolean reflushAccessToken() throws Exception{
        //找出所有渠道
        List<WocChannel> wocChannels  = channelService.findAllChannel();

        for(WocChannel wocChannel :wocChannels){
            if(StringUtils.isEmpty(wocChannel.getAppid())|| StringUtils.isEmpty(wocChannel.getAppSecret())){
                //不需要更新accessToken
                continue;
            }
            String url = null;
            String accessToken = null;
            if(wocChannel.getChannelType() == ChannelType.WX_SUBSCRIBE.getValue()){   //微信公众号
                url = systemConfig.getWeixinAccessTokenUrl().replace("APPID",wocChannel.getAppid())
                        .replace("APPSECRET",wocChannel.getAppSecret());
                if(StringUtils.isNotEmpty(url)){

                    String result = HttpUtils.doGet(url);
                    if(StringUtils.isNotEmpty(result)){
                        accessToken = JSONObject.parseObject(result).getString("access_token");

                    }
                }
            }else if(wocChannel.getChannelType() == ChannelType.WX_XCX.getValue()){
                WxMaService wxMaService = weixinConfig.getAppMapService().get(wocChannel.getId());
                accessToken= wxMaService.getAccessToken();
            }

            if(StringUtils.isNotEmpty(accessToken)){
                redisUtils.set(Contants.ACCESSTOKEN_KEY+wocChannel.getId(),accessToken);
            }

        }

        return true;
    }


}
