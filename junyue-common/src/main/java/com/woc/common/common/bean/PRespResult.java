package com.woc.common.common.bean;

import com.alibaba.fastjson.JSONObject;
import com.woc.common.common.RespContants;

public class PRespResult<T> {




    protected Class<T> clazz;

    private int code;

    private String msg;

    private Object data;

    private int count;

    public PRespResult(T data,int count){

        this.code = 200;
        this.msg = "success";
        this.count = count;
        setData(data);
    }
    public PRespResult(int code , String msg , T data){
        this.code =code;
        this.msg = msg;
        setData(data);
    }
    public PRespResult(int code , String msg){
        this.code =code;
        this.msg = msg;

    }
    public PRespResult(RespContants contants){
        this.code = contants.getCode();
        this.msg = contants.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int conut) {
        this.count = conut;
    }

    public Object getData() {
        if(data != null){
            if(data instanceof Boolean){
                return data;
            }else{

                return JSONObject.parseObject(JSONObject.toJSONString(data),clazz);
            }

        }
        return null;
    }

    public void setData(Object data) {
//        String s = JSONObject.toJSONString(data);
//        if(data instanceof Collection){
//            this.data = JSONArray.parseArray(s);
//        }else{
//            this.data = JSONObject.parseObject(s);
//
//        }
        this.data = data;
    }
}
