package com.woc.cms.admin.interceptor;

import com.woc.common.common.RespContants;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.common.exception.CmsLoginException;
import com.woc.cms.admin.annotation.CmsIgnoreLogin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CmsLoginInterceptor extends HandlerInterceptorAdapter {





    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {



        try{
//            //添加跨域CORS
//            response.setHeader("Access-Control-Allow-Origin", "http://localhost");
//            response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,token");
//            response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            CmsIgnoreLogin methodAnnotation = handlerMethod.getMethodAnnotation(CmsIgnoreLogin.class);

            //有注解跳过
            if(methodAnnotation != null){
                return true;
            }
            Subject currentUser = SecurityUtils.getSubject();

            if(currentUser.getPrincipal() == null){
                throw new CmsLoginException(RespContants.USERINFO_ERROR);
            }


            return true;
        }catch (CmsLoginException e){
            throw e;
        }catch (Exception e){
            throw new BusinessException(404,"接口不存在");
        }



    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

}
