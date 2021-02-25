package com.woc.woc.message.config.rabbitMQ;

import com.woc.common.common.Contants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class RabbitQueueConfig {



    @Resource(name = "messageDirectExchange" )
    private DirectExchange messageDirectExchange;

    @Resource(name = "orderDirectExchange" )
    private DirectExchange orderDirectExchange;

    @Resource(name = "orderTllDirectExchange" )
    private DirectExchange orderTllExchange;

    //微信推送队列
    @Bean
    public Queue weixinMessagequeue(){
        return new Queue(Contants.WEIXIN_MESSAGE_QUEUE);
    }

    @Bean
    public Binding weixinMessageBinding(){
        return BindingBuilder.bind(weixinMessagequeue()).to(messageDirectExchange).with(Contants.WEIXIN_MESSAGE_QUEUE);
    }

    //订单取消队列
    @Bean
    public Queue orderCancelQueue(){
        return new Queue(Contants.ORDER_CANCEL_QUEUE);
    }

    @Bean
    public Binding orderCancelBinding(){
        return BindingBuilder.bind(orderCancelQueue()).to(orderDirectExchange).with(Contants.ORDER_CANCEL_QUEUE);
    }
    //订单延时队列
    @Bean
    public Queue orderTllQueue(){
        return QueueBuilder
                .durable(Contants.ORDER_TLL_QUEUE)
                // 配置到期后转发的交换
                .withArgument("x-dead-letter-exchange", Contants.ORDER_DIRECT_EXCHANGE)
                // 配置到期后转发的路由键
                .withArgument("x-dead-letter-routing-key", Contants.ORDER_CANCEL_QUEUE)
                .build();
    }
    @Bean
    public  Binding orderTllBinging(){
        return BindingBuilder.bind(orderTllQueue()).to(orderTllExchange).with(Contants.ORDER_TLL_QUEUE);
    }



}
