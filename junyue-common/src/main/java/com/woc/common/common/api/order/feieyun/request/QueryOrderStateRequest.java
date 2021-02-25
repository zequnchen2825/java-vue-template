package com.woc.common.common.api.order.feieyun.request;

import com.woc.common.common.api.order.feieyun.BaseRequest;
import com.woc.common.common.api.order.feieyun.response.QueryOrderStateResponse;

import java.util.HashMap;
import java.util.Map;

public class QueryOrderStateRequest implements BaseRequest<QueryOrderStateResponse> {

    private String orderid;

    public QueryOrderStateRequest(String orderid) {
        this.orderid = orderid;
    }

    @Override
    public String getApiMethodName() {
        return "Open_queryOrderState";
    }

    @Override
    public String getApiDisplayName() {
        return "查询订单是否打印成功";
    }

    @Override
    public String getHttpMethod() {
        return "POST";
    }

    @Override
    public Map<String, Object> getTextParams() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("orderid",orderid);
        return param;
    }

    @Override
    public Class<QueryOrderStateResponse> getResponseClass() {
        return QueryOrderStateResponse.class;
    }

    @Override
    public boolean isNeedSign() {
        return false;
    }
}
