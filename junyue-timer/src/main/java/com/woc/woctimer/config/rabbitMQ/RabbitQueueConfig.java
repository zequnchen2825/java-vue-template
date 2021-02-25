package com.woc.woctimer.config.rabbitMQ;

import com.woc.common.common.Contants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

//@Configuration
public class RabbitQueueConfig {



    @Resource
    private DirectExchange dealUserCourseExchange;

    @Bean
    public Queue dealUserCoursequeue(){
        return new Queue(Contants.dealUserCoursequeue);
    }

    @Bean
    public Binding weixinMessageBinding(){
        return BindingBuilder.bind(dealUserCoursequeue()).to(dealUserCourseExchange).with(Contants.dealUserCoursequeue);
    }


}
