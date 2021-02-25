package com.woc.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableTransactionManagement
@SpringBootApplication
@ComponentScan("com.woc")
@MapperScan(value = "com.woc.dao.dao.**")
@EnableCaching  //开启缓存
public class Application {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(Application.class, args);

    }

}


