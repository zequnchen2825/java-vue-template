package com.woc.common.common.api.order.feieyun.request;

import com.woc.common.common.api.order.feieyun.BaseRequest;
import com.woc.common.common.api.order.feieyun.response.DeletePrinterResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeletePrinterRequest implements BaseRequest<DeletePrinterResponse> {

    private List<String> sns;

    public DeletePrinterRequest(List<String> sns) {
        this.sns = sns;
    }

    @Override
    public String getApiMethodName() {
        return "Open_printerDelList";
    }

    @Override
    public String getApiDisplayName() {
        return "删除批量打印机";
    }

    @Override
    public String getHttpMethod() {
        return "POST";
    }

    @Override
    public Map<String, Object> getTextParams() throws Exception {
        StringBuffer buf = new StringBuffer();
        for(String sn :sns){
            if(buf.length()>1){
                buf.append("-");
            }
            buf.append(sn);
        }
        Map<String, Object> param = new HashMap<>();
        param.put("snlist",buf.toString());
        return param;
    }

    @Override
    public Class<DeletePrinterResponse> getResponseClass() {
        return DeletePrinterResponse.class;
    }

    @Override
    public boolean isNeedSign() {
        return false;
    }
}
