package com.woc.common.common.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.woc.common.common.RespContants;

import java.util.Collection;

public class RespResult<T> {




    protected Class<T> clazz;

    private int code;

    private String msg;

    private Object data;

    private Long count;

    public RespResult( T data){

        this.code =200;
        this.msg = "success";
        setData(data);
    }
    public RespResult( Boolean data){

        this.code =200;
        this.msg = "success";
        this.data = data;
    }

    public RespResult( ){

        this.code =200;
        this.msg = "success";
    }
    public RespResult(int code , String msg , T data){
        this.code =code;
        this.msg = msg;
        setData(data);
    }
    public RespResult(int code , String msg){
        this.code =code;
        this.msg = msg;

    }
    public RespResult(RespContants contants){
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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
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
