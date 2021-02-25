package com.woc.common.vo.enumVO;

public enum TimeTypeEnum {
    SEND(1,"配送时间"),
    OPEN(2,"营业时间"),
    SPECIAL_SEND(3,"专送时间"),
    MERCHANT_SPECIAl_SEND(4,"商店专送时间");

    TimeTypeEnum(Integer type,String timeRemark){
        this.type = type;
        this.timeRemark = timeRemark;
    }
    private Integer type;

    private String timeRemark;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTimeRemark() {
        return timeRemark;
    }

    public void setTimeRemark(String timeRemark) {
        this.timeRemark = timeRemark;
    }
}
