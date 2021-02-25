package com.woc.common.common.api.order.elianyun.request;

import com.woc.common.common.api.order.elianyun.ELianYunBaseRequset;
import com.woc.common.common.api.order.elianyun.response.ELianYunOauthResponse;
import com.woc.common.common.api.order.elianyun.response.ELianYunPrinterResponse;

import java.util.HashMap;
import java.util.Map;

public class ELianYunPrinterRequest implements ELianYunBaseRequset<ELianYunPrinterResponse> {

    private String machineCode;

    private String content;

    private String originId;

    public ELianYunPrinterRequest(String machineCode, String content, String originId) {
        this.machineCode = machineCode;
        this.content = content;
        this.originId = originId;
    }

    @Override
    public String getApiMethodName() {
        return "/print/index";
    }

    @Override
    public String getApiDisplayName() {
        return "文本打印";
    }

    @Override
    public String getHttpMethod() {
        return "POST";
    }

    @Override
    public Map<String, Object> getTextParams() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("machine_code",machineCode);
        params.put("content",content);
        params.put("origin_id",originId);
        return params;
    }

    @Override
    public Class<ELianYunPrinterResponse> getResponseClass() {
        return ELianYunPrinterResponse.class;
    }

    @Override
    public boolean isNeedSign() {
        return false;
    }
}
