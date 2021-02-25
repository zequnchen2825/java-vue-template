package com.woc.woc.message.config.rabbitMQ;

import com.woc.common.common.Contants;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitExchangeConfig {


    @Bean(autowire = Autowire.BY_NAME,value="messageDirectExchange")
    public DirectExchange MessageDirectExchange(){
        return (DirectExchange) ExchangeBuilder
                .directExchange(Contants.MESSAGE_DIRECT_EXCHANGE)
                .durable(true)
                .build();

    }

    @Bean(autowire = Autowire.BY_NAME,value="orderDirectExchange")
    public DirectExchange OrderDirectExchange(){
        return (DirectExchange) ExchangeBuilder
                .directExchange(Contants.ORDER_DIRECT_EXCHANGE)
                .durable(true)
                .build();
    }
    @Bean(autowire = Autowire.BY_NAME,value="orderTllDirectExchange")
    public DirectExchange OrderTllDirectExchange(){
        return (DirectExchange) ExchangeBuilder
                .directExchange(Contants.ORDER_TLL_EXCHANGE)
                .durable(true)
                .build();
    }

//    @Bean
//    public FanoutExchange MessageFanoutExchange(){
//        FanoutExchange messageFanoutExchange = new FanoutExchange(Contants.MESSAGE_FANOUT_EXCHANGE);
//        return messageFanoutExchange;
//    }
}
