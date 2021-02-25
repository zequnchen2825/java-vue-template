package com.woc.common.common.api.order;

public enum PrinterTypeEnum {
    P_365(1),
    P_FEIE(2),
    P_ELIANYUN(3)
    ;


    private Integer type;

    PrinterTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
