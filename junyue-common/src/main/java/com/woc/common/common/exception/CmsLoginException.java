package com.woc.common.common.exception;

import com.woc.common.common.RespContants;
import org.apache.commons.lang3.StringUtils;

public class CmsLoginException extends BusinessException {
    public CmsLoginException(int code,String msg){
        super(code, msg);
    }
    public CmsLoginException(RespContants contants){
        super(contants.getCode(), contants.getMsg());
    }
    public CmsLoginException(RespContants contants,String remark){
        super(contants.getCode(), contants.getMsg());
        this.remark = remark;
    }


    private String remark;


    @Override
    public String getMsg() {
        if(StringUtils.isNotEmpty(remark)){
            return super.getMsg()+remark;

        }
        return super.getMsg();
    }

}
