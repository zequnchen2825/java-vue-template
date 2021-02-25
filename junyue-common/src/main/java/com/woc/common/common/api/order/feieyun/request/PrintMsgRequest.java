package com.woc.common.common.api.order.feieyun.request;

import com.woc.common.common.api.order.feieyun.BaseRequest;
import com.woc.common.common.api.order.feieyun.response.PrintMsgResponse;

import java.util.HashMap;
import java.util.Map;

public class PrintMsgRequest implements BaseRequest<PrintMsgResponse> {

    private String sn;

    private String content;

    public PrintMsgRequest(String sn, String content) {
        this.sn = sn;
        this.content = content;
    }

    @Override
    public String getApiMethodName() {
        return "Open_printMsg";
    }

    @Override
    public String getApiDisplayName() {
        return "打印订单";
    }

    @Override
    public String getHttpMethod() {
        return "POST";
    }

    @Override
    public Map<String, Object> getTextParams() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("sn",sn);
        param.put("content",content);
        return param;
    }

    @Override
    public Class<PrintMsgResponse> getResponseClass() {
        return PrintMsgResponse.class;
    }

    @Override
    public boolean isNeedSign() {
        return false;
    }
}
