package com.woc.common.common.api.order.elianyun.request;

import com.woc.common.common.api.order.elianyun.ELianYunBaseRequset;
import com.woc.common.common.api.order.elianyun.response.ELianYunPrinterResponse;
import com.woc.common.common.api.order.elianyun.response.ELianYunQueryOrderResponse;

import java.util.HashMap;
import java.util.Map;

public class ELianYunQueryOrderRequest implements ELianYunBaseRequset<ELianYunQueryOrderResponse> {

    private String machineCode;

    private String orderId;

    public ELianYunQueryOrderRequest(String machineCode, String orderId) {
        this.machineCode = machineCode;
        this.orderId = orderId;
    }

    @Override
    public String getApiMethodName() {
        return "/printer/getorderstatus";
    }

    @Override
    public String getApiDisplayName() {
        return "获取订单状态接口";
    }

    @Override
    public String getHttpMethod() {
        return "POST";
    }

    @Override
    public Map<String, Object> getTextParams() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("machine_code",machineCode);
        params.put("order_id",orderId);
        return params;
    }

    @Override
    public Class<ELianYunQueryOrderResponse> getResponseClass() {
        return ELianYunQueryOrderResponse.class;
    }

    @Override
    public boolean isNeedSign() {
        return false;
    }
}
