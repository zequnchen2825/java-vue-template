package com.woc.dao.cache;

import java.lang.annotation.*;

/**
 *  @author: chenzequn
 *  @Date: 2020/3/12 14:54
 *  @Description: 缓存注解
 */
@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisCache {
    String key();

    RedisTimeEnum time();

    Class[] tableDao();


}
