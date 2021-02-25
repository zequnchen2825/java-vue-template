package com.woc.web.controller;


import com.woc.dao.dao.TUserDao;

import com.woc.service.service.template.IMsgTemplateService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerApplicationTests {

//    @Autowired
//    private IMsgTemplateService iMsgTemplateService;
//    @Autowired
//    private TUserDao tUserDao;
//
//    @Autowired
//    private MongoDatabase mongoDatabase;
//    @Test
//    public void contextLoads2() throws Exception {
//        Map<String,Object> params = new HashMap<>();
//        params.put("first","第03,04节:体育课(马兰芳404)\\r\\n\\r\\n第06,07节:毛泽东社会主义概论(黄浩川601)\\r\\n\\r\\n第09,10节:毛泽东社会主义概论(黄浩川601)\\r\\n\\r\\n第11,12节:毛泽东社会主义概论(黄浩川601)\\r\\n\\r\\n第13,14节:毛泽东社会主义概论(黄浩川601)");
//        iMsgTemplateService.sendMsgTemplate("201910110001",tUserDao.selectByPrimaryKey(1),params);
//    }
//    @Test
//    public void contextLoads() {
//
//        //表达式解析器
//        ExpressionParser parser = new SpelExpressionParser();
//        EvaluationContext context = new StandardEvaluationContext();
//        Map<String,Object> map = new HashMap<>();
//        map.put("name","tom");
//        map.put("age","18");
//
//        ((StandardEvaluationContext) context).setVariables(map);
//
//        Object value = parser.parseExpression("他的名字为#{#name},今年#{#age}岁", new TemplateParserContext()).getValue(context);// 他的名字为Tom
//
//        System.out.println(value);
//    }
//
//    @Test
//    public void testMongo(){
//
//        MongoCollection<Document> collection = mongoDatabase.getCollection("testCollection1");
//        //insert
//        Document document = new Document();
//        document.put("name","陈泽群");
//        document.put("sex","12");
//        document.put("age","19");
//        document.put("isHandsome","yes");
//
//        collection.insertOne(document);
//
//        //find
//        FindIterable<Document> documents = collection.find();
//        System.out.println("-------搜索全部-------");
//        for(Document document1 : documents){
//            System.out.println(document1.toJson());
//        }
//
//        Bson filter = Filters.eq("name", "帅哥群");
//
//        BasicDBObject cond1= new BasicDBObject();
//
//        cond1.append("name","陈泽群");
//        cond1.append("sex","12");
////        DeleteResult deleteResult = collection.deleteMany(cond1);
////        System.out.println("删除数量"+deleteResult.getDeletedCount());
//
//
//        cond1.append("_id",new ObjectId("5dbbe9608778cf3618049697"));
//
//        FindIterable<Document> documents1 = collection.find(cond1);
//        System.out.println("-------搜索条件-------");
//        for(Document document1 : documents1){
//            System.out.println(document1.toJson());
//        }
//    }

}
