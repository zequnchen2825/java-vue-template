package com.woc.woc.message.weixin;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.woc.common.common.Contants;
import com.woc.common.common.RespContants;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.common.utils.RedisUtils;
import com.woc.common.common.utils.SpringELUtils;
import com.woc.common.vo.MsgTemplateVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import weixin.popular.api.MessageAPI;
import weixin.popular.bean.message.templatemessage.TemplateMessage;
import weixin.popular.bean.message.templatemessage.TemplateMessageItem;
import weixin.popular.bean.message.templatemessage.TemplateMessageResult;

import java.io.IOException;
import java.util.LinkedHashMap;

@Component
@RabbitListener(queues = Contants.WEIXIN_MESSAGE_QUEUE)
public class WeiXinMessage {
    private static Logger log = LoggerFactory.getLogger(WeiXinMessage.class);

    @Autowired
    private RedisUtils redisUtils;

    @RabbitHandler
    public void send(MsgTemplateVO msgTemplateVO) throws Exception{
        log.info("发送消息："+JSONObject.toJSONString(msgTemplateVO));

        try {
            String content = SpringELUtils.replaceEL(msgTemplateVO.getMsgTemplate().getTemplate(), msgTemplateVO.getParams());
            String accessToken = redisUtils.getStr(Contants.ACCESSTOKEN_KEY + msgTemplateVO.getUser().getChannelId());
            TemplateMessage templateMessage = new TemplateMessage();
            templateMessage.setTouser(msgTemplateVO.getUser().getOpenId());
            templateMessage.setUrl(msgTemplateVO.getMsgTemplate().getUrl());
            templateMessage.setTemplate_id(msgTemplateVO.getMsgTemplate().getTemplateId());
            LinkedHashMap<String, TemplateMessageItem> linkedHashMap = JSONObject.parseObject(content, LinkedHashMap.class);

            templateMessage.setData(linkedHashMap);
            TemplateMessageResult templateMessageResult = MessageAPI.messageTemplateSend(accessToken, templateMessage);
            if("40001".equals(templateMessageResult.getErrcode())){
                throw new BusinessException(RespContants.ACCESSTOKEN_ERROR);
            }

            log.info("发送完成："+JSONObject.toJSONString(templateMessageResult));
        } catch (BusinessException e){
            throw e;
        } catch (Exception e){
            log.error("发送微信接口出错"+e.getMessage());
        }


    }
}
