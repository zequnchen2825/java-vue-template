package com.woc.common.common.api.wxPay.request;

import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.util.SignUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.woc.common.common.utils.Md5Util;
import com.woc.common.common.utils.SHA1Util;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.TreeMap;

@XStreamAlias("xml")
public class WxPayPersonRequest {

    @XStreamAlias("mch_appid")
    private String mchAppid;
    @XStreamAlias("mchid")
    private String mchid;
    @XStreamAlias("nonce_str")
    private String nonceStr = String.valueOf(System.currentTimeMillis());
    @XStreamAlias("device_info")
    private String deviceInfo;
    @XStreamAlias("sign")
    private String sign;
    @XStreamAlias("partner_trade_no")
    private String partnerTradeNo =String.valueOf(System.currentTimeMillis());
    @XStreamAlias("openid")
    private String openid;
    @XStreamAlias("check_name")
    private String checkName = "NO_CHECK";
    @XStreamAlias("re_user_name")
    private String reUserName;
    @XStreamAlias("amount")
    private String amount;
    @XStreamAlias("desc")
    private String desc;
    @XStreamAlias("spbill_create_ip")
    private String spbillCreateIp;

    private String key;

    public String toParamString() throws Exception {
        Map<String,Object> param = new TreeMap<>();
        param.put("mch_appid",getMchAppid());
        param.put("mchid",getMchid());
        param.put("nonce_str",getNonceStr());
        param.put("device_info",getDeviceInfo());
        param.put("partner_trade_no",getPartnerTradeNo());
        param.put("openid",getOpenid());
        param.put("check_name",getCheckName());
        param.put("re_user_name",getReUserName());
        param.put("amount",getAmount());
        param.put("desc",getDesc());
        param.put("spbill_create_ip",getSpbillCreateIp());
        StringBuffer buf = new StringBuffer();
        for(Map.Entry<String,Object> entry : param.entrySet()){
            if(entry.getValue() !=null){
                if(buf.length() != 0 ){
                    buf.append("&");
                }
                buf.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        buf.append("&key=").append(key);

        return Md5Util.md5(buf.toString(),"").toUpperCase();
    }

    public String toXML() throws Exception {
        setSign(toParamString());
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(this.getClass());
        xstream.omitField(this.getClass(),"key");
        return xstream.toXML(this);
    }
    public String getMchAppid() {
        return mchAppid;
    }

    public void setMchAppid(String mchAppid) {
        this.mchAppid = mchAppid;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerTradeNo() {
        return partnerTradeNo;
    }

    public void setPartnerTradeNo(String partnerTradeNo) {
        this.partnerTradeNo = partnerTradeNo;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getReUserName() {
        return reUserName;
    }

    public void setReUserName(String reUserName) {
        this.reUserName = reUserName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
