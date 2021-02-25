package com.woc.service.service.template.impl;

import com.alibaba.fastjson.JSONObject;
import com.woc.common.common.Contants;
import com.woc.common.common.RespContants;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.common.utils.SpringELUtils;
import com.woc.common.entity.MsgTemplate;
import com.woc.common.entity.TUser;
import com.woc.common.entity.WocChannel;
import com.woc.common.vo.MsgTemplateVO;
import com.woc.dao.dao.MsgTemplateDao;
import com.woc.service.service.login.ChannelService;
import com.woc.service.service.template.IMsgTemplateService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weixin.popular.api.MessageAPI;
import weixin.popular.api.WxaAPI;
import weixin.popular.bean.message.templatemessage.TemplateMessage;

import java.util.Map;

@Service
public class MsgTemplateServiceImpl implements IMsgTemplateService {

    @Autowired
    private MsgTemplateDao msgTemplateDao;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public boolean sendMsgTemplate(String templateId,TUser tUser,Map<String,Object> params) throws Exception {
        MsgTemplate template = findMsgTemplateById(templateId);
        if(!template.getChannelId().equals( tUser.getChannelId())){
            throw new BusinessException(RespContants.TEMPLATE_CHANNEL_ERROR);
        }
        String params1 = template.getParams();
        JSONObject paramsJson = JSONObject.parseObject(params1);
        for(String key :paramsJson.keySet()){
            if(params.get(key) == null){
                throw new BusinessException(RespContants.TEMPLATE_PARAM_ERROR,",参数名："+key+"为空");
            }
        }
        WocChannel channel = channelService.findChannelById(tUser.getChannelId());

        if(channel.getChannelType()==2){    //微信公众号
            MsgTemplateVO msgTemplateVO = new MsgTemplateVO();
            msgTemplateVO.setMsgTemplate(template);
            msgTemplateVO.setUser(tUser);
            msgTemplateVO.setParams(params);
            rabbitTemplate.convertAndSend(Contants.WEIXIN_MESSAGE_QUEUE,msgTemplateVO);
            return true;
        }
        return false;
    }

    @Override
    public MsgTemplate findMsgTemplateById(String templateId) throws Exception {
        MsgTemplate msgTemplate = msgTemplateDao.selectByPrimaryKey(templateId);
        if(msgTemplate == null){
            throw  new BusinessException(RespContants.TEMPLATE_ID_ERROR);
        }
        return msgTemplate;
    }
}
