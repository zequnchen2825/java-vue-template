package com.woc.common.common.api.order.order365.request;

import com.woc.common.common.api.order.order365.BaseRequest;
import com.woc.common.common.api.order.order365.response.AddOrderResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AddOrderRequest implements BaseRequest<AddOrderResponse> {
    private final String API_METHOD="/addOrder";
    private final String HTTP_METHOD="POST";

    private String printContent;

    private Integer times = 1;


    public AddOrderRequest(String printContent) {
        this.printContent = printContent;
    }

    public String getPrintContent() {
        return printContent;
    }

    public void setPrintContent(String printContent) {
        this.printContent = printContent;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    @Override
    public String getApiMethodName() {
        return API_METHOD;
    }

    @Override
    public String getApiDisplayName() {
        return "打印内容";
    }

    @Override
    public String getHttpMethod() {
        return HTTP_METHOD;
    }

    @Override
    public Map<String, Object> getTextParams() {
        Map<String,Object> params = new HashMap<>();
        params.put("printContent",printContent);
        params.put("times",times);
        return params;
    }

    @Override
    public Class<AddOrderResponse> getResponseClass() {
        return AddOrderResponse.class;
    }

    @Override
    public boolean isNeedSign() {
        return false;
    }
}
