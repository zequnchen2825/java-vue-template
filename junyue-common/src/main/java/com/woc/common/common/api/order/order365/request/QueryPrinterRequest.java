package com.woc.common.common.api.order.order365.request;

import com.woc.common.common.api.order.order365.BaseRequest;
import com.woc.common.common.api.order.order365.BaseResponse;
import com.woc.common.common.api.order.order365.response.AddOrderResponse;

import java.util.HashMap;
import java.util.Map;

public class QueryPrinterRequest implements BaseRequest<BaseResponse> {
    private final String API_METHOD="/queryPrinterStatus";
    private final String HTTP_METHOD="POST";


    @Override
    public String getApiMethodName() {
        return API_METHOD;
    }

    @Override
    public String getApiDisplayName() {
        return "查询打印机的状态";
    }

    @Override
    public String getHttpMethod() {
        return HTTP_METHOD;
    }

    @Override
    public Map<String, Object> getTextParams() {
        Map<String,Object> params = new HashMap<>();
        return params;
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
