package com.woc.common.common.exception;

import com.woc.common.common.RespContants;
import org.apache.shiro.ShiroException;

public class ShiroExtException extends ShiroException {
    private int code;

    private String msg;

    public ShiroExtException(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
    public ShiroExtException(RespContants contants){
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
}
