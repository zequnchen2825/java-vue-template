package com.woc.common.bo;

import com.woc.common.common.utils.DateUtils;
import com.woc.common.entity.extend.PageParams;

import java.util.Date;

public class OrderBO extends PageParams {

    private Integer schoolId;

    private String mobile;

    private Date startTime;

    private Date endTime;

    private Integer merchantId;

    private Integer sendType;

    private Integer actionId;

    private String verifyCode;
    /**
     *  0.等待商家出餐 1.等待配送 2.配送完成 3.确认收货
     */
    private Integer status;
    /**
     * -1.已取消 0.待支付 1.已支付 2.已退款 3代退款 4 部分退款，5拒绝退款 已完成
     */
    private Integer payStatus;

    private boolean runnerAble = false;

    private Integer runnerId;

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) throws Exception {
        this.startTime = DateUtils.parse(startTime,"yyyy-MM-dd");
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) throws Exception {
        this.endTime = DateUtils.parse(endTime,"yyyy-MM-dd");
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public boolean getRunnerAble() {
        return runnerAble;
    }

    public void setRunnerAble(boolean runnerAble) {
        this.runnerAble = runnerAble;
    }


    public Integer getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(Integer runnerId) {
        this.runnerId = runnerId;
    }
}
