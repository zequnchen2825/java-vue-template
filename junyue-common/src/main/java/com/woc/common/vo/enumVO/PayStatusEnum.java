package com.woc.common.vo.enumVO;

public enum PayStatusEnum {

    //-1.已取消 0.待支付 1.已支付 2.已退款
    CANNEL(-1),
    WAIT(0),
    PAYED(1),
    REFUNN(2);

    private Integer payStatus;

    PayStatusEnum(Integer payStatus){
        this.payStatus = payStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
}
