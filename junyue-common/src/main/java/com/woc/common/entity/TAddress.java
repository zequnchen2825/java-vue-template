package com.woc.common.entity;

import java.io.Serializable;

/**
 * t_address
 * @author 
 */
public class TAddress implements Serializable {
    private Integer id;

    /**
     * 收件名
     */
    private String name;

    /**
     * 收件手机号
     */
    private String mobile;

    /**
     * 宿舍楼id
     */
    private Integer dormitoryBuildId;

    /**
     * 宿舍号码
     */
    private String dormitoryNumber;

    private Integer isDelete;

    /**
     * 用户id
     */
    private Integer userId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getDormitoryBuildId() {
        return dormitoryBuildId;
    }

    public void setDormitoryBuildId(Integer dormitoryBuildId) {
        this.dormitoryBuildId = dormitoryBuildId;
    }

    public String getDormitoryNumber() {
        return dormitoryNumber;
    }

    public void setDormitoryNumber(String dormitoryNumber) {
        this.dormitoryNumber = dormitoryNumber;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
        TAddress other = (TAddress) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getDormitoryBuildId() == null ? other.getDormitoryBuildId() == null : this.getDormitoryBuildId().equals(other.getDormitoryBuildId()))
            && (this.getDormitoryNumber() == null ? other.getDormitoryNumber() == null : this.getDormitoryNumber().equals(other.getDormitoryNumber()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getDormitoryBuildId() == null) ? 0 : getDormitoryBuildId().hashCode());
        result = prime * result + ((getDormitoryNumber() == null) ? 0 : getDormitoryNumber().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", mobile=").append(mobile);
        sb.append(", dormitoryBuildId=").append(dormitoryBuildId);
        sb.append(", dormitoryNumber=").append(dormitoryNumber);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}