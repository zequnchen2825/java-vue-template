package com.woc.common.common.api.order.elianyun.request;

import com.woc.common.common.api.order.elianyun.ELianYunBaseRequset;
import com.woc.common.common.api.order.elianyun.response.ELianYunAddPrinterResponse;

import java.util.HashMap;
import java.util.Map;

public class ELianYunAddPrinterRequest implements ELianYunBaseRequset<ELianYunAddPrinterResponse> {

    private String machineCode;
    private String msign;

    public ELianYunAddPrinterRequest(String machineCode, String msign) {
        this.machineCode = machineCode;
        this.msign = msign;
    }

    @Override
    public String getApiMethodName() {
        return "/printer/addprinter";
    }

    @Override
    public String getApiDisplayName() {
        return "终端授权 (永久授权)";
    }

    @Override
    public String getHttpMethod() {
        return "POST";
    }

    @Override
    public Map<String, Object> getTextParams() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("machine_code",machineCode);
        param.put("msign",msign);
        return param;
    }

    @Override
    public Class<ELianYunAddPrinterResponse> getResponseClass() {
        return ELianYunAddPrinterResponse.class;
    }

    @Override
    public boolean isNeedSign() {
        return false;
    }
}
