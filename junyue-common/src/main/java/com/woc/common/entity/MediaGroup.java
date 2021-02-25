package com.woc.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * media_group
 * @author 
 */
public class MediaGroup implements Serializable {
    private Integer id;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 分组tag
     */
    private String groupTag;

    /**
     * 渠道id
     */
    private Integer channelId;

    /**
     * 缩略图宽
     */
    private Double width;

    /**
     * 缩略图高
     */
    private Double height;

    /**
     * 媒体宽
     */
    private Double mediaWidth;

    /**
     * 媒体高
     */
    private Double mediaHeight;

    /**
     * 是否删除
     */
    private Integer isDelete;

    private Date createTime;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupTag() {
        return groupTag;
    }

    public void setGroupTag(String groupTag) {
        this.groupTag = groupTag;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getMediaWidth() {
        return mediaWidth;
    }

    public void setMediaWidth(Double mediaWidth) {
        this.mediaWidth = mediaWidth;
    }

    public Double getMediaHeight() {
        return mediaHeight;
    }

    public void setMediaHeight(Double mediaHeight) {
        this.mediaHeight = mediaHeight;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MediaGroup other = (MediaGroup) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGroupName() == null ? other.getGroupName() == null : this.getGroupName().equals(other.getGroupName()))
            && (this.getGroupTag() == null ? other.getGroupTag() == null : this.getGroupTag().equals(other.getGroupTag()))
            && (this.getChannelId() == null ? other.getChannelId() == null : this.getChannelId().equals(other.getChannelId()))
            && (this.getWidth() == null ? other.getWidth() == null : this.getWidth().equals(other.getWidth()))
            && (this.getHeight() == null ? other.getHeight() == null : this.getHeight().equals(other.getHeight()))
            && (this.getMediaWidth() == null ? other.getMediaWidth() == null : this.getMediaWidth().equals(other.getMediaWidth()))
            && (this.getMediaHeight() == null ? other.getMediaHeight() == null : this.getMediaHeight().equals(other.getMediaHeight()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGroupName() == null) ? 0 : getGroupName().hashCode());
        result = prime * result + ((getGroupTag() == null) ? 0 : getGroupTag().hashCode());
        result = prime * result + ((getChannelId() == null) ? 0 : getChannelId().hashCode());
        result = prime * result + ((getWidth() == null) ? 0 : getWidth().hashCode());
        result = prime * result + ((getHeight() == null) ? 0 : getHeight().hashCode());
        result = prime * result + ((getMediaWidth() == null) ? 0 : getMediaWidth().hashCode());
        result = prime * result + ((getMediaHeight() == null) ? 0 : getMediaHeight().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", groupName=").append(groupName);
        sb.append(", groupTag=").append(groupTag);
        sb.append(", channelId=").append(channelId);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", mediaWidth=").append(mediaWidth);
        sb.append(", mediaHeight=").append(mediaHeight);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", createTime=").append(createTime);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}