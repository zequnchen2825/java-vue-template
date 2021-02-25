package com.woc.common.vo.enumVO;

public enum ChannelType {

    OFFICIAL(1),
    WX_SUBSCRIBE(2),
    WX_XCX(3);


    private int value;

    private ChannelType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
