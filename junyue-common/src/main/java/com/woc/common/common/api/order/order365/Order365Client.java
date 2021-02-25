package com.woc.common.common.api.order.order365;

import com.alibaba.fastjson.JSONObject;
import com.woc.common.common.api.order.BaseClient;
import com.woc.common.common.api.order.order365.request.AddOrderRequest;
import com.woc.common.common.api.order.order365.request.QueryOrderRequest;
import com.woc.common.common.api.order.order365.request.QueryPrinterRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class Order365Client extends BaseClient {

    private  String  deviceNo;
    private  String  key;
    private static final String BASE_URL="http://open.printcenter.cn:8080";

    public Order365Client(String deviceNo, String key) {
        this.deviceNo = deviceNo;
        this.key = key;
    }

    public <T extends BaseResponse>T execute(BaseRequest<T> request) throws Exception {
        log.info("请求365单打机接口:{},请求内容：{}",request.getApiDisplayName(),JSONObject.toJSONString(request));
        StringBuffer buf = new StringBuffer();
        buf.append(BASE_URL).append(request.getApiMethodName());
        Map<String, Object> params = request.getTextParams();
        params.put("deviceNo",deviceNo);
        params.put("key",key);

        String responseStr = executeKV(request.getHttpMethod(),params,buf);
        T t = JSONObject.parseObject(responseStr, request.getResponseClass());
        if(t.getResponseCode() == 0){
            t.setResponseCode(200);
        }
        return t;
    }

    public static void main(String[] args) throws Exception {
        Order365Client order365Client = new Order365Client("kdt21017351","02cee");
        QueryPrinterRequest queryPrinterRequest = new QueryPrinterRequest();
        QueryOrderRequest queryOrderRequest = new QueryOrderRequest("12082971911578235496904");
        StringBuffer content= new StringBuffer();
        content.append("<C>*****翼达校园外卖单*******</C><BR>");
        content.append("<CB>大众餐(三楼2饭)</CB><BR>");
        content.append("<CB>外卖(早餐)</CB><BR>");
        content.append("[订单编号]00001-20190105-00001<BR>");
        content.append("[下单时间]2019-01-05 14:00<BR>");
        content.append("[备注]帮我下辣椒<BR>");
        content.append("--------------------------------<BR>");
        content.append("<L>鱼香肉丝饭           ×1  13.8</L><BR>");
        content.append("-------------------------------<BR>");
        content.append("满减      30.0   合计      59.3<BR>");
        content.append("<B>思诚C6104</B><BR>");
        content.append("群帅哥[下单次数:5]<BR>");
        content.append("18814182825<BR>");

//15715713251578203506505
        AddOrderRequest renRequest = new AddOrderRequest(content.toString());
        QueryPrinterRequest request = new QueryPrinterRequest();
//        BaseResponse response = order365Client.execute(renRequest);
        BaseResponse execute = order365Client.execute(request);

        System.out.println(JSONObject.toJSONString(execute));
    }
}
