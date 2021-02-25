package com.woc.common.common.api.order.elianyun;

import com.alibaba.fastjson.JSONObject;
import com.woc.common.common.api.order.BaseClient;
import com.woc.common.common.api.order.elianyun.request.ELianYunAddPrinterRequest;
import com.woc.common.common.api.order.elianyun.request.ELianYunOauthRequest;
import com.woc.common.common.api.order.elianyun.request.ELianYunPrinterRequest;
import com.woc.common.common.api.order.elianyun.request.ELianYunQueryPrinterRequest;
import com.woc.common.common.api.order.elianyun.response.ELianYunAddPrinterResponse;
import com.woc.common.common.api.order.elianyun.response.ELianYunQueryPrinterResponse;
import com.woc.common.common.utils.Md5Util;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;

@Slf4j
public class ELianyunClient extends BaseClient {

    private static final String CLIENT_ID = "1066679814";
    private static final String CLIENT_SECRET = "deb4303738838dbafed40192da3cd09c";
    private static final String BASE_URL = "https://open-api.10ss.net";
    private static final String ACCESS_TOKEN = "f2be856e44a241a683a9a40f95fc473f";
    private static final String REFRESH_TOKEN = "728a64c90b184a968da6a745f2800acd";


    public <T extends ELianYunBaseResponse>T execute(ELianYunBaseRequset<T> request) throws Exception {
        log.info("请求易联云打单机接口:{},请求内容：{}",request.getApiDisplayName(), JSONObject.toJSONString(request));
        StringBuffer buf = new StringBuffer();
        Map<String, Object> params = request.getTextParams();
        params.put("timestamp",System.currentTimeMillis()/1000);
        params.put("id", UUID.randomUUID().toString());
        params.put("client_id", CLIENT_ID);
        params.put("sign", Md5Util.md5(CLIENT_ID+params.get("timestamp"),CLIENT_SECRET));
        params.put("access_token", ACCESS_TOKEN);
        buf.append(BASE_URL+request.getApiMethodName());

        String responseStr = executeKV(request.getHttpMethod(),params,buf);
        T t = JSONObject.parseObject(responseStr, request.getResponseClass());
        if(t.getError().equals("0")){
            t.setError("200");
        }
        return t;
    }

    public static void main(String[] args) throws Exception {
        ELianyunClient eLianyunClient = new ELianyunClient();
        ELianYunOauthRequest request = new ELianYunOauthRequest();
//        ELianYunOauthResponse response = eLianyunClient.execute(request);

        StringBuffer content= new StringBuffer();
        content.append("<center>*****翼达校园外卖单*******</center>\n");
        content.append("<center><FS2>大众餐(三楼2饭)</FS2></center>\n");
        content.append("<center><FS2>外卖(早餐)</FS2></center>\n");
        content.append("[订单编号]00001-20190105-00001\n");
        content.append("[下单时间]2019-01-05 14:00\n");
        content.append("[备注]帮我下辣椒\n");
        content.append("--------------------------------\n");
        content.append("<FH2>鱼香肉丝饭           ×1  13.8</FH2>\n");
        content.append("-------------------------------\n");
        content.append("满减      30.0   合计      59.3\n");
        content.append("<FS2>思诚C6104</FS2>\n");
        content.append("群帅哥[下单次数:5]\n");
        content.append("18814182825");
//        ELianYunPrinterRequest request1 = new ELianYunPrinterRequest("4004600341",content.toString(),"2019010500001");
//        ELianYunPrinterResponse response = eLianyunClient.execute(request1);
        //444760287
//        ELianYunQueryOrderRequest request1 = new ELianYunQueryOrderRequest("4004600341","444760287");
//        ELianYunQueryOrderResponse response = eLianyunClient.execute(request1);
        ELianYunAddPrinterRequest request1 = new ELianYunAddPrinterRequest("4004600341","8mfb23au6nub");
//        ELianYunQueryPrinterRequest request2 = new ELianYunQueryPrinterRequest("4004600341");
        ELianYunAddPrinterResponse execute = eLianyunClient.execute(request1);
//        ELianYunQueryPrinterResponse execute = eLianyunClient.execute(request2);
        System.out.println(JSONObject.toJSONString(execute));
    }
}
