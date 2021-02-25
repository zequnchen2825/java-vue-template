package com.woc.service.service.template;

import com.woc.common.entity.MsgTemplate;
import com.woc.common.entity.TUser;

import java.util.Map;

public interface IMsgTemplateService {

    public boolean sendMsgTemplate(String templateId,TUser tUser,Map<String,Object> params) throws Exception;

    public MsgTemplate findMsgTemplateById(String templateId) throws Exception;
}
