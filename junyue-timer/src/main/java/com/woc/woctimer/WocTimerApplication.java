package com.woc.woctimer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.woc")
@MapperScan(value = "com.woc.dao.dao.**")
@EnableScheduling
public class WocTimerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WocTimerApplication.class, args);
    }

}
