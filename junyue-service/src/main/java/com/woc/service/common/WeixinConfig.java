package com.woc.service.common;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.woc.common.entity.WocChannel;
import com.woc.common.vo.enumVO.ChannelType;
import com.woc.dao.dao.WocChannelDao;
import com.woc.service.service.login.ChannelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

@Configuration
public class WeixinConfig {

    private  HashMap<Integer, WxMaService> appMapService;
    private  HashMap<Integer, WxPayService> wxPayServices;

    @Autowired
    private ChannelService channelService;

//    @Bean
    public void services(){
        List<WocChannel> allChannel = channelService.findAllChannel();
        HashMap<Integer, WxMaService> appMapService = new HashMap<>();
        HashMap<Integer, WxPayService> wxPayServices = new HashMap<>();
        for(WocChannel wocChannel:allChannel){
            if(wocChannel.getChannelType() == ChannelType.WX_XCX.getValue()){

                WxMaInMemoryConfig config = new WxMaInMemoryConfig();
                config.setAppid(wocChannel.getAppid());
                config.setSecret(wocChannel.getAppSecret());
//                config.set

                WxMaServiceImpl wxMaService = new WxMaServiceImpl();
                wxMaService.setWxMaConfig(config);
                appMapService.put(wocChannel.getId(),wxMaService);
            }
            if(StringUtils.isNotEmpty(wocChannel.getMchId())){
                WxPayConfig payConfig = new WxPayConfig();
                payConfig.setMchId(wocChannel.getMchId());
                payConfig.setMchKey(wocChannel.getMchKey());
        //    payConfig.setSubAppId(StringUtils.trimToNull(this.properties.getSubAppId()));
        //    payConfig.setSubMchId(StringUtils.trimToNull(this.properties.getSubMchId()));
                URL certUrl = Thread.currentThread().getContextClassLoader().getResource("apiclient_cert.p12");
                payConfig.setKeyPath(certUrl.getPath());

                // 可以指定是否使用沙箱环境
                payConfig.setUseSandboxEnv(false);

                WxPayService wxPayService = new WxPayServiceImpl();
                wxPayService.setConfig(payConfig);
                wxPayServices.put(wocChannel.getId(),wxPayService);
            }


        }

        this.appMapService = appMapService;
        this.wxPayServices = wxPayServices;

    }

    public HashMap<Integer, WxMaService> getAppMapService() {
        return appMapService;
    }
    public HashMap<Integer, WxPayService> getWxPayService() {
        return wxPayServices;
    }
}
