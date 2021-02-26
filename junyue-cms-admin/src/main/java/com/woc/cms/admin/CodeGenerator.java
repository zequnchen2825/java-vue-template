package com.woc.cms.admin;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setActiveRecord(true);
        String projectPath = System.getProperty("user.dir");
//        gc.setOutputDir(projectPath + "/junyue-cms-admin/src/main/java/");
        gc.setAuthor("zequn.chen");
        gc.setOpen(false);
        gc.setFileOverride(false);
        gc.setBaseResultMap(true);//生成resultMap
        gc.setBaseColumnList(true);//在xml中生成基础列
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/walking_on_the_code?characterEncoding=utf8&useSSL=true&serverTimezone=GMT%2B8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("输入模块名"));
        pc.setParent("com.woc");
        pc.setServiceImpl(".service.service."+pc.getModuleName()+".impl");
        pc.setService(".service.service."+pc.getModuleName());
        pc.setMapper(".dao.dao."+pc.getModuleName());
        pc.setEntity(".common.entity."+pc.getModuleName());
        pc.setController(".cms.admin.controller."+pc.getModuleName());
        pc.setPathInfo(new HashMap<>());
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(
                new FileOutConfig("/selfTemplates/mapper.xml.ftl") {
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                        return projectPath + "/junyue-dao/src/main/resources/mapper/" + pc.getModuleName()
                                + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                    }
                }
        );
        focList.add(
                new FileOutConfig("/selfTemplates/mapper.java.ftl") {
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                        return projectPath + "/junyue-dao/src/main/java/com/woc/dao/dao/" + pc.getModuleName()
                                + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_JAVA;
                    }
                }
        );
        focList.add(
                new FileOutConfig("/selfTemplates/entity.java.ftl") {
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                        return projectPath + "/junyue-common/src/main/java/com/woc/common/entity/" + pc.getModuleName()
                                + "/" + tableInfo.getEntityName()+ StringPool.DOT_JAVA;
                    }
                }
        );
        focList.add(
                new FileOutConfig("/selfTemplates/service.java.ftl") {
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                        return projectPath + "/junyue-service/src/main/java/com/woc/service/service/" + pc.getModuleName()
                                + "/I" + tableInfo.getEntityName()+"Service"+ StringPool.DOT_JAVA;
                    }
                }
        );
        focList.add(
                new FileOutConfig("/selfTemplates/serviceImpl.java.ftl") {
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                        return projectPath + "/junyue-service/src/main/java/com/woc/service/service/" + pc.getModuleName()
                                + "/impl/" + tableInfo.getEntityName()+"ServiceImpl"+ StringPool.DOT_JAVA;
                    }
                }
        );
        focList.add(
                new FileOutConfig("/selfTemplates/controller.java.ftl") {
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                        return projectPath + "/junyue-cms-admin/src/main/java/com/woc/cms/admin/controller/" + pc.getModuleName()+"/"
                                +  tableInfo.getEntityName()+"Controller"+ StringPool.DOT_JAVA;
                    }
                }
        );
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDirs(filePath);
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                if (fileType == FileType.CONTROLLER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                if (fileType == FileType.SERVICE) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                if (fileType == FileType.SERVICE_IMPL) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("/selfTemplates/entity.java");
        templateConfig.setService("/selfTemplates/service.java");
        templateConfig.setServiceImpl("/selfTemplates/serviceImpl.java");
        templateConfig.setController("/selfTemplates/controller.java");
        templateConfig.setMapper("/selfTemplates/mapper.java");
        templateConfig.setXml("/selfTemplates/mapper.xml");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("t_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    private static void checkDirs(String filePath) {
        File file = new File(filePath);
        boolean exist = file.exists();
        if (!exist) {
            file.getParentFile().mkdirs();
        }
    }
}