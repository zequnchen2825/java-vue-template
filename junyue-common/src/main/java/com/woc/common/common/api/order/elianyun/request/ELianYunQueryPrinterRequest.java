package com.woc.common.common.api.order.elianyun.request;

import com.woc.common.common.api.order.elianyun.ELianYunBaseRequset;
import com.woc.common.common.api.order.elianyun.response.ELianYunAddPrinterResponse;
import com.woc.common.common.api.order.elianyun.response.ELianYunQueryPrinterResponse;

import java.util.HashMap;
import java.util.Map;

public class ELianYunQueryPrinterRequest implements ELianYunBaseRequset<ELianYunQueryPrinterResponse> {

    private String machineCode;


    public ELianYunQueryPrinterRequest(String machineCode) {
        this.machineCode = machineCode;

    }

    @Override
    public String getApiMethodName() {
        return "/printer/getprintstatus";
    }

    @Override
    public String getApiDisplayName() {
        return "获取终端状态接口";
    }

    @Override
    public String getHttpMethod() {
        return "POST";
    }

    @Override
    public Map<String, Object> getTextParams() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("machine_code",machineCode);
        return param;
    }

    @Override
    public Class<ELianYunQueryPrinterResponse> getResponseClass() {
        return ELianYunQueryPrinterResponse.class;
    }

    @Override
    public boolean isNeedSign() {
        return false;
    }
}
