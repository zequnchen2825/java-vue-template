package com.woc.common.vo;

import com.woc.common.entity.MsgTemplate;
import com.woc.common.entity.TUser;

import java.io.Serializable;
import java.util.Map;

public class MsgTemplateVO implements Serializable {

    private TUser user;

    private MsgTemplate msgTemplate;

    private Map<String,Object> params;


    public TUser getUser() {
        return user;
    }

    public void setUser(TUser user) {
        this.user = user;
    }

    public MsgTemplate getMsgTemplate() {
        return msgTemplate;
    }

    public void setMsgTemplate(MsgTemplate msgTemplate) {
        this.msgTemplate = msgTemplate;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
