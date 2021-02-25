package com.woc.cms.admin.config;

import com.woc.cms.admin.shiro.MyShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 * 1.配置ShiroFilterFactory 2.配置SecurityManager
 * @author zhengkai
 *
 */
@Configuration
public class ShiroConfig {
    /**
     * 配置shiro过滤器
     * @author zhengkai
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        //1.定义shiroFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //2.设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //3.LinkedHashMap是有序的，进行顺序拦截器配置
        Map<String,String> filterChainMap = new LinkedHashMap<String,String>();
        //4.配置logout过滤器
        filterChainMap.put("/logout", "logout");
        //5.所有url必须通过认证才可以访问
        filterChainMap.put("/*/login","anon");
        filterChainMap.put("/**","anon");
//        filterChainMap.put("/**","authc");
        //6.设置默认登录的url
        shiroFilterFactoryBean.setLoginUrl("/login");
        //7.设置成功之后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //8.设置未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/login");
        //9.设置shiroFilterFactoryBean的FilterChainDefinitionMap
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }
    /**
     * 配置安全管理器
     * @author zhengkai
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());

        securityManager.setSessionManager(sessionManager());

        return securityManager;
    }

    /**
     * shiro session的管理
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setSessionDAO(redisSessionDAO());
        Cookie cookie = new SimpleCookie("WOCJSESSIONID");
//        cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);

        return sessionManager;
    }

    @Bean
    public MyShiroRealm myShiroRealm(){
        return new MyShiroRealm();
    }

}
