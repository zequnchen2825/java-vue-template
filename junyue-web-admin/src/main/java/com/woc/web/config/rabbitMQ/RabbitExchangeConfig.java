package com.woc.web.config.rabbitMQ;

import com.woc.common.common.Contants;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitExchangeConfig {
//
//
//    @Bean
//    public DirectExchange MessageDirectExchange(){
//        DirectExchange messageDirectExchange = new DirectExchange(Contants.directExchange);
//        return messageDirectExchange;
//    }
//
//    @Bean
//    public FanoutExchange MessageFanoutExchange(){
//        FanoutExchange messageFanoutExchange = new FanoutExchange(Contants.fanoutExchange);
//        return messageFanoutExchange;
//    }
}
