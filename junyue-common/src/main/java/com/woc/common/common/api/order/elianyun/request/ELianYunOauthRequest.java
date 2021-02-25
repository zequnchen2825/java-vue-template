package com.woc.common.common.api.order.elianyun.request;

import com.woc.common.common.api.order.elianyun.ELianYunBaseRequset;
import com.woc.common.common.api.order.elianyun.response.ELianYunOauthResponse;

import java.util.HashMap;
import java.util.Map;

public class ELianYunOauthRequest implements ELianYunBaseRequset<ELianYunOauthResponse> {


    @Override
    public String getApiMethodName() {
        return "/oauth/oauth";
    }

    @Override
    public String getApiDisplayName() {
        return "获取Access Token";
    }

    @Override
    public String getHttpMethod() {
        return "POST";
    }

    @Override
    public Map<String, Object> getTextParams() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("scope","all");
        params.put("grant_type","client_credentials");
        return params;
    }

    @Override
    public Class<ELianYunOauthResponse> getResponseClass() {
        return ELianYunOauthResponse.class;
    }

    @Override
    public boolean isNeedSign() {
        return false;
    }
}
