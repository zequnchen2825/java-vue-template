package com.woc.common.common.api.order;

import com.woc.common.common.utils.HttpUtils;

import java.util.Map;

public class BaseClient {

    protected String executeKV(String httpMethod,Map<String, Object> params, StringBuffer buf) throws Exception {

        if("GET".equals(httpMethod)){
            for(Map.Entry<String, Object> entry : params.entrySet()){
                if(buf.toString().indexOf("?") == -1){
                    buf.append("?").append(entry.getKey()).append("=").append(entry.getValue().toString());
                }else{
                    buf.append("&").append(entry.getKey()).append("=").append(entry.getValue().toString());
                }
            }
            return HttpUtils.doGet(buf.toString());
        }else{
            return HttpUtils.doPostKV(buf.toString(), params);
        }
    }
}
