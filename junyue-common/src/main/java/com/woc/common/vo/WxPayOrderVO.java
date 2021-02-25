package com.woc.common.vo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;

public class WxPayOrderVO {

    private String timeStamp;

    @JSONField(name="package")
    private String packageValue;

    private String paySign;

    private String signType;

    private String nonceStr;

    public WxPayOrderVO(WxPayMpOrderResult wxPayMpOrderResult){
        this.nonceStr = wxPayMpOrderResult.getNonceStr();
        this.packageValue = wxPayMpOrderResult.getPackageValue();
        this.paySign = wxPayMpOrderResult.getPaySign();
        this.timeStamp = wxPayMpOrderResult.getTimeStamp();
        this.signType = wxPayMpOrderResult.getSignType();
    }
    public WxPayOrderVO(JSONObject wxPayMpOrderResult){
        this.nonceStr = wxPayMpOrderResult.getString("nonceStr");
        this.packageValue = wxPayMpOrderResult.getString("packageValue");
        this.paySign = wxPayMpOrderResult.getString("paySign");
        this.timeStamp = wxPayMpOrderResult.getString("timeStamp");
        this.signType = wxPayMpOrderResult.getString("signType");
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }
}
