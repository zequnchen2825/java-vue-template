package com.woc.common.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring工具类,获取Spring容器中的Bean
 * @author chenzequn
 *
 */
@Component("springUtil")
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext ctx = null;
    /**
     * 获取bean
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String id){
        return (T) ctx.getBean(id);
    }
    /**
     * 按类型获取bean
     * @param clazz
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return ctx.getBean(clazz);
    }
    /**
     * 按类型及ID获取bean
     * @param id
     * @param clazz
     * @return
     */
    public static <T> T getBean(String id, Class<T> clazz){
        return ctx.getBean(id, clazz);
    }
    /**
     *
     * 检查SpringUtil是否已完成初始化
     * @param
     * @return boolean
     * @throws
     */
    public static boolean isInited(){
        return null!=ctx;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.ctx == null) {
            SpringUtil.ctx = applicationContext;
        }
    }
}

