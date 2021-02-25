package com.woc.woctimer.config.rabbitMQ;

import com.woc.common.common.Contants;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RabbitExchangeConfig {


    @Bean
    public DirectExchange DealUserCourseExchange(){
        DirectExchange dealUserCourseExchange = new DirectExchange(Contants.dealUserCourseExchange);
        return dealUserCourseExchange;
    }

}
