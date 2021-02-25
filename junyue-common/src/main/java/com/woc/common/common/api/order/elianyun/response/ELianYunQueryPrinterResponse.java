package com.woc.common.common.api.order.elianyun.response;

import com.woc.common.common.api.order.elianyun.ELianYunBaseResponse;
import lombok.Data;


public class ELianYunQueryPrinterResponse extends ELianYunBaseResponse {

    private Body body;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public class Body{
        private Integer state;

        private String status;

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        public String getStatus() {
            switch (state){
                case 0:
                    return "离线";
                case 1:
                    return "在线";
                case 2:
                    return "缺纸";
                default:
                    return "";
            }

        }
    }
}
