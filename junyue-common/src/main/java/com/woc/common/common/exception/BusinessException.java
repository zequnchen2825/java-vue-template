package com.woc.common.common.exception;


import com.woc.common.common.RespContants;
import org.apache.commons.lang3.StringUtils;

public class BusinessException extends RuntimeException {

    public BusinessException(int code,String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    public BusinessException(RespContants contants){
        super(contants.getMsg());
        this.code = contants.getCode();
        this.msg = contants.getMsg();
    }
    public BusinessException(String msg){
        super(msg);
        this.code = 4001;
        this.msg = msg;
    }
    public BusinessException(RespContants contants,String remark){
        super(contants.getMsg());
        this.code = contants.getCode();
        this.msg = contants.getMsg();
        this.remark = remark;
    }
    public BusinessException(RespContants contants,String remark,Object data){
        super(contants.getMsg());
        this.code = contants.getCode();
        this.msg = contants.getMsg();
        this.remark = remark;
        this.data = data;
    }
    private int code;

    private String msg;

    private String remark;

    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        if(StringUtils.isNotEmpty(remark)){
            return msg+remark;
        }
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
