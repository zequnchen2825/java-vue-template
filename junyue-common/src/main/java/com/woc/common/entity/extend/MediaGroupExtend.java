package com.woc.common.entity.extend;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.woc.common.entity.MediaGroup;
import com.woc.common.entity.WocMenu;

import java.util.Date;
import java.util.List;

public class MediaGroupExtend extends MediaGroup {

    private String channelName;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private List<WocMediaExtend> wocMediaExtends;

    private Integer platformId;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }



    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<WocMediaExtend> getWocMediaExtends() {
        return wocMediaExtends;
    }

    public void setWocMediaExtends(List<WocMediaExtend> wocMediaExtends) {
        this.wocMediaExtends = wocMediaExtends;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }
}
