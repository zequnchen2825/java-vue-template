package com.woc.common.common.api.order.feieyun;

public class BaseResponse {

    private Integer ret;

    private Integer serverExecutedTime;

    private String msg;

    public Integer getRet() {
        return ret;
    }

    public void setRet(Integer ret) {
        this.ret = ret;
    }

    public Integer getServerExecutedTime() {
        return serverExecutedTime;
    }

    public void setServerExecutedTime(Integer serverExecutedTime) {
        this.serverExecutedTime = serverExecutedTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
