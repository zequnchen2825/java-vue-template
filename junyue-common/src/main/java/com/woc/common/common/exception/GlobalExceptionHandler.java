package com.woc.common.common.exception;

import com.woc.common.common.bean.RespResult;
import com.woc.common.common.config.BaseConfig;
import com.woc.common.common.utils.RedisUtils;
import com.woc.common.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private BaseConfig baseConfig;

    /**
     * @ExceptionHandler 表示拦截异常
     *    @ControllerAdvice 是 controller 的一个辅助类，最常用的就是作为全局异常处理的切面类
     *    @ControllerAdvice 可以指定扫描范围
     *    @ControllerAdvice 约定了几种可行的返回值，如果是直接返回 model 类的话，需要使用 @ResponseBody 进行 json 转换
     *    返回 String，表示跳到某个 view
     *    返回 modelAndView
     *    返回 model + @ResponseBody
     */
    /**
     * 返回json字符串的 还有返回modeAndView的
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RespResult exceptionHandler(Exception e){

        e.printStackTrace();
        if(e.getCause() instanceof ShiroExtException){
            ShiroExtException se = (ShiroExtException)e.getCause();
            return new RespResult(se.getCode(),se.getMsg());
        }
        return new RespResult(4000,"网络异常"+e.getMessage());
    }
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public RespResult exceptionHandler(BusinessException e){
        log.error("错误码"+e.getCode()+",错误信息"+e.getMsg());
        return new RespResult(e.getCode(),e.getMsg(),e.getData());
    }

    @ExceptionHandler(CmsLoginException.class)
    @ResponseBody
    public RespResult exceptionHandler(CmsLoginException e){

        e.printStackTrace();
        //登录页
         String url = baseConfig.getBaseUrl()+"woc-cms-front/dist/html/pages/login.html";
//         String url = "http://127.0.0.1/woc-cms-front/dist/html/pages/login.html";
         return new RespResult(e.getCode(),e.getMsg(),url);
    }


}