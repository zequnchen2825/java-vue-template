package com.woc.common.common.api.order.elianyun.response;

import com.woc.common.common.api.order.elianyun.ELianYunBaseResponse;

public class ELianYunPrinterResponse extends ELianYunBaseResponse {


    private Body body;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public class Body{
        String id;
        String origin_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrigin_id() {
            return origin_id;
        }

        public void setOrigin_id(String origin_id) {
            this.origin_id = origin_id;
        }
    }

}
