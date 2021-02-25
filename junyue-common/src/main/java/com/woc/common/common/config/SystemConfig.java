package com.woc.common.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {

    @Value("${spring.profiles.include}")
    private String env;
    @Value("${weixin.accessToken.url}")
    private String weixinAccessTokenUrl;

    public String getEnv() {
        return env;
    }

    public String getWeixinAccessTokenUrl() {
        return weixinAccessTokenUrl;
    }

}
