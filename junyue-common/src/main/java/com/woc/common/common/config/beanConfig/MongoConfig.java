package com.woc.common.common.config.beanConfig;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

//    @Value("${spring.mongodb.host}")
//    private String host;
//    @Value("${spring.mongodb.port}")
//    private int port;
//    @Value("${spring.mongodb.database}")
//    private String dataBase;
//
//    @Bean
//    public MongoDatabase mongoDatabase(){
//        // 连接到 mongodb 服务
//        MongoClient mongoClient = new MongoClient( host , port);
//
//
//        // 连接到数据库
//        MongoDatabase mongoDatabase = mongoClient.getDatabase(dataBase);
//
//        return mongoDatabase;
//    }
}
