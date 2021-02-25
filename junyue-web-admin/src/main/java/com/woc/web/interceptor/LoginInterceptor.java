package com.woc.web.interceptor;

import com.woc.common.common.Contants;
import com.woc.common.common.RespContants;
import com.woc.common.common.config.BaseConfig;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.entity.*;
import com.woc.dao.dao.*;
import com.woc.web.annotation.IgnoreLogin;
import com.woc.web.annotation.IgnoreSubPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private BaseConfig baseConfig;

    @Autowired
    private TUserDao tUserDao;
    @Autowired
    private WocPlatformDao wocPlatformDao;
    @Autowired
    private WocChannelDao wocChannelDao;
    @Autowired
    private WocBusinessDao wocBusinessDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {




        HandlerMethod handlerMethod = (HandlerMethod) handler;

        IgnoreLogin methodAnnotation = handlerMethod.getMethodAnnotation(IgnoreLogin.class);

        if(methodAnnotation != null){
            return true;
        }
        HttpSession session = request.getSession();

        TUser user = (TUser)session.getAttribute(Contants.USER);

        WocPlatform wocPlatform = (WocPlatform)session.getAttribute(Contants.PLATFORM);
        WocChannel wocChannel = (WocChannel)session.getAttribute(Contants.CHANNEL);
        WocBusiness wocBusiness = (WocBusiness) session.getAttribute(Contants.BUSINESS);

        if(user == null || wocPlatform == null || wocChannel == null || wocBusiness == null){
            if(Arrays.asList(baseConfig.getEnv().split(",")).contains("dev")){
                user =  tUserDao.selectByPrimaryKey(1);
                wocPlatform = wocPlatformDao.selectByPrimaryKey(3);
                wocChannel = wocChannelDao.selectByPrimaryKey(4);
                wocBusiness = wocBusinessDao.selectByPrimaryKey(2);
                session.setAttribute(Contants.PLATFORM,wocPlatform);
                session.setAttribute(Contants.CHANNEL,wocChannel);
                session.setAttribute(Contants.BUSINESS,wocBusiness);
            }else{

                throw new BusinessException(RespContants.USERINFO_ERROR);
            }
//            throw new BusinessException(RespContants.USERINFO_ERROR);

        }
        request.setAttribute(Contants.USER,user);
        request.setAttribute("wocPlatform",wocPlatform);
        request.setAttribute("wocChannel",wocChannel);
        request.setAttribute("wocBusiness",wocBusiness);

        IgnoreSubPassword ignoreSubPassword = handlerMethod.getMethodAnnotation(IgnoreSubPassword.class);

//        if(ignoreSubPassword != null){
//            return false;
//        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

}
