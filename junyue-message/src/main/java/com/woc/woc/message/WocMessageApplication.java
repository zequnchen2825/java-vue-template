package com.woc.woc.message;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.woc")
@MapperScan(value = "com.woc.dao.dao.**")
public class WocMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(WocMessageApplication.class, args);
    }

}
