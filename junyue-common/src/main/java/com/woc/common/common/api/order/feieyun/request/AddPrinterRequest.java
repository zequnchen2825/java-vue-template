package com.woc.common.common.api.order.feieyun.request;

import com.woc.common.common.RespContants;
import com.woc.common.common.api.order.feieyun.BaseRequest;
import com.woc.common.common.api.order.feieyun.response.AddPrinterResponse;
import com.woc.common.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  @author: chenzequn
 *  @Date: 2020/1/6 14:19
 *  @Description:  添加打单机
 */
public class AddPrinterRequest implements BaseRequest<AddPrinterResponse> {

    private List<Printer> printers;

    public AddPrinterRequest(List<Printer> printers){
        this.printers = printers;
    }

    public AddPrinterRequest(){

    }

    public class Printer{
        private String sn;

        private String key;

        private String remark;

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
    @Override
    public String getApiMethodName() {
        return "Open_printerAddlist";
    }

    @Override
    public String getApiDisplayName() {
        return "批量添加打印机";
    }

    @Override
    public String getHttpMethod() {
        return "POST";
    }

    @Override
    public Map<String, Object> getTextParams() throws Exception {
        StringBuffer buf = new StringBuffer();
        for(Printer printer:printers){
            if(StringUtils.isNotEmpty(printer.getSn())){
                buf.append(printer.getSn());
            }else{
                throw new BusinessException(RespContants.PARAMS_ISNOT_RIGHT);
            }
            if(StringUtils.isNotEmpty(printer.getKey())){
                buf.append("#");
                buf.append(printer.getKey());
            }else{
                throw new BusinessException(RespContants.PARAMS_ISNOT_RIGHT);
            }
            if(StringUtils.isNotEmpty(printer.getRemark())){
                buf.append("#");
                buf.append(printer.getRemark());
            }
            buf.append("\n");
        }
        Map<String,Object> param = new HashMap<>();
        param.put("printerContent",buf.toString());
        return param;
    }

    @Override
    public Class<AddPrinterResponse> getResponseClass() {
        return AddPrinterResponse.class;
    }

    @Override
    public boolean isNeedSign() {
        return false;
    }
}
