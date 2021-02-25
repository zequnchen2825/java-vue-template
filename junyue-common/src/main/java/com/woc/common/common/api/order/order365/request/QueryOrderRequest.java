package com.woc.common.common.api.order.order365.request;

import com.woc.common.common.api.order.order365.BaseRequest;
import com.woc.common.common.api.order.order365.BaseResponse;

import java.util.HashMap;
import java.util.Map;

public class QueryOrderRequest implements BaseRequest<BaseResponse> {

    private static final String API_METHOD ="/queryOrder";
    private static final String API_NAME ="查询订单是否打印成功";
    private static final String HTTP_METHOD ="POST";


    private String orderIndex;

    public QueryOrderRequest(String orderIndex) {
        this.orderIndex = orderIndex;
    }

    @Override
    public String getApiMethodName() {
        return API_METHOD;
    }

    @Override
    public String getApiDisplayName() {
        return API_NAME;
    }

    @Override
    public String getHttpMethod() {
        return HTTP_METHOD;
    }

    @Override
    public Map<String, Object> getTextParams() {
        Map<String, Object> param = new HashMap<>();
        param.put("orderindex",orderIndex);
        return param;
    }

    @Override
    public Class<BaseResponse> getResponseClass() {
        return BaseResponse.class;
    }

    @Override
    public boolean isNeedSign() {
        return false;
    }
}
