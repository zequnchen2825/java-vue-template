package com.woc.common.common.api.wxPay;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.impl.WxPayServiceApacheHttpImpl;
import com.github.binarywang.wxpay.service.impl.WxPayServiceJoddHttpImpl;
import com.github.binarywang.wxpay.util.SignUtils;
import com.thoughtworks.xstream.XStream;
import com.woc.common.common.api.wxPay.request.WxPayPersonRequest;
import com.woc.common.common.api.wxPay.response.WxPayPersonResult;
import com.woc.common.common.utils.HttpUtils;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;

public class WxPayServiceClient extends WxPayServiceJoddHttpImpl {

    private static final String baseUrl="https://api.mch.weixin.qq.com";

    public WxPayPersonResult payPerson(WxPayPersonRequest request) throws Exception {
        String url = baseUrl + "/mmpaymkttransfers/promotion/transfers";

        String xml = HttpUtils.doPostXML(url, request.toXML(), request.getMchid());
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(WxPayPersonResult.class);
//        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypesByWildcard(new String[] {
                "com.woc.**"
        });
        xstream.setClassLoader(WxPayPersonResult.class.getClassLoader());
        WxPayPersonResult result = (WxPayPersonResult)xstream.fromXML(xml);


        return result;
    }

    public static void main(String[] args) throws Exception {
        WxPayServiceClient client = new WxPayServiceClient();
        WxPayPersonRequest request = new WxPayPersonRequest();
        request.setAmount("50");
        request.setCheckName("NO_CHECK");
        request.setOpenid("oYPcu5SmqwSWGZxk5U7y4wP85Hzs");
        request.setMchAppid("wx14b2be7f1bace851");
        request.setMchid("1528325641");
        request.setPartnerTradeNo("20200121000001");
        request.setDesc("11");
        request.setSpbillCreateIp("192.168.0.1");
        request.setKey("7d39a5cdc48f62565b48fefe472e66b3");

        WxPayPersonResult wxPayPersonResult = client.payPerson(request);
        System.out.println(JSONObject.toJSONString(wxPayPersonResult));
    }


}
