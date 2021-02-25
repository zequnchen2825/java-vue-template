package com.woc.common.entity.extend.DO;

import com.woc.common.entity.WocMedia;

public class WocMediaExtendDO extends WocMedia {

    private String order;

    private Integer platformId;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }
}
