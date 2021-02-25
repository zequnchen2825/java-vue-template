package com.woc.common.vo.enumVO;

public enum  StatusEnum {
    WAIT_MERCHAT(0),
    WAIT_SEND(1),
    SEND_FINISH(2),
    RECEIVE(3);

    private Integer status;
    StatusEnum(Integer status){
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
