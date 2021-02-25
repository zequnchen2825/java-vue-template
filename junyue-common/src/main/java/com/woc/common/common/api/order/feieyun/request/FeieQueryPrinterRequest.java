package com.woc.common.common.api.order.feieyun.request;

import com.woc.common.common.RespContants;
import com.woc.common.common.api.order.feieyun.BaseRequest;
import com.woc.common.common.api.order.feieyun.response.AddPrinterResponse;
import com.woc.common.common.api.order.feieyun.response.FeieQueryPrinterResponse;
import com.woc.common.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  @author: chenzequn
 *  @Date: 2020/1/6 14:19
 *  @Description:  查询打单机状态
 */
public class FeieQueryPrinterRequest implements BaseRequest<FeieQueryPrinterResponse> {

    private String sn;

    public FeieQueryPrinterRequest(String sn) {
        this.sn = sn;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String getApiMethodName() {
        return "Open_queryPrinterStatus";
    }

    @Override
    public String getApiDisplayName() {
        return "查询打印机状态";
    }

    @Override
    public String getHttpMethod() {
        return "POST";
    }

    @Override
    public Map<String, Object> getTextParams() throws Exception {
        Map<String,Object> param = new HashMap<>();
        param.put("sn",sn);
        return param;
    }

    @Override
    public Class<FeieQueryPrinterResponse> getResponseClass() {
        return FeieQueryPrinterResponse.class;
    }

    @Override
    public boolean isNeedSign() {
        return false;
    }
}
