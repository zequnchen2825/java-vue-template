package com.woc.common.common.api.order.feieyun.response;

import com.woc.common.common.api.order.feieyun.BaseResponse;

import java.util.List;

public class AddPrinterResponse extends BaseResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private List<String> ok;
        private List<String> no;

        public List<String> getOk() {
            return ok;
        }

        public void setOk(List<String> ok) {
            this.ok = ok;
        }

        public List<String> getNo() {
            return no;
        }

        public void setNo(List<String> no) {
            this.no = no;
        }
    }
}
