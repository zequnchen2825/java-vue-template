package com.woc.common.common.api.order.feieyun;

import com.alibaba.fastjson.JSONObject;
import com.woc.common.common.api.order.BaseClient;
import com.woc.common.common.api.order.feieyun.request.*;
import com.woc.common.common.api.order.feieyun.response.AddPrinterResponse;
import com.woc.common.common.api.order.feieyun.response.DeletePrinterResponse;
import com.woc.common.common.api.order.feieyun.response.FeieQueryPrinterResponse;
import com.woc.common.common.api.order.feieyun.response.QueryOrderStateResponse;
import com.woc.common.common.utils.ShaUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class FeieClient extends BaseClient {

    private static final String  USER = "657335188@qq.com";
    private static final String  UKEY = "yCRTdtd4UxnUEYcF";
    private static final String BASE_URL="http://api.feieyun.cn/Api/Open/";



    public <T extends BaseResponse>T execute(BaseRequest<T> request) throws Exception {
        log.info("请求飞鹅打单机接口:{},请求内容：{}",request.getApiDisplayName(),JSONObject.toJSONString(request));
        StringBuffer buf = new StringBuffer();
        Map<String, Object> params = request.getTextParams();
        params.put("user",USER);
        params.put("stime",System.currentTimeMillis()/1000);
        params.put("sig", ShaUtil.shaEncode(USER+UKEY+params.get("stime")));
        params.put("apiname",request.getApiMethodName());
        buf.append(BASE_URL);

        String responseStr = executeKV(request.getHttpMethod(),params,buf);
        T t = JSONObject.parseObject(responseStr, request.getResponseClass());
        if(t.getRet() == 0){
            t.setRet(200);
        }
        return t;
    }

    public static void main(String[] args) throws Exception {
        FeieClient client = new FeieClient();

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
//550513253_20200106165343_1529830590
        PrintMsgRequest request = new PrintMsgRequest("550513253",content.toString());
        QueryOrderStateRequest queryOrderRequest = new QueryOrderStateRequest("550513253_20200106165343_1529830590");
        FeieQueryPrinterRequest queryPrinterRequest = new FeieQueryPrinterRequest("550513253");

        List<String> list = new ArrayList<>();
        list.add("550513253");
        DeletePrinterRequest deletePrinterRequest = new DeletePrinterRequest(list);
//        QueryOrderStateResponse response = order365Client.execute(queryOrderRequest);
//        DeletePrinterResponse execute = client.execute(deletePrinterRequest);
        List<AddPrinterRequest.Printer> list1 = new ArrayList<>();
        AddPrinterRequest.Printer printer = new AddPrinterRequest().new Printer();

        printer.setSn("550513253");
        printer.setKey("8n2smf8q");
        list1.add(printer);
        AddPrinterRequest addPrinterRequest = new AddPrinterRequest(list1);
        AddPrinterResponse execute = client.execute(addPrinterRequest);

        System.out.println(JSONObject.toJSONString(execute));
    }
}
