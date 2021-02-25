package com.woc.dao.cache.aspect;

import com.woc.dao.cache.RedisCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Aspect
@Component
public class CacheAspect {


    @Pointcut("@annotation(com.woc.dao.cache.RedisCache)")
    public void CachePointCut() {}

    /**
     * 环绕通知 @Around  ， 当然也可以使用 @Before (前置通知)  @After (后置通知)
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("CachePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        try {
            Signature sig = point.getSignature();

            MethodSignature msig = (MethodSignature) sig;

            Object target = point.getTarget();

            Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
            Class<?> returnType = currentMethod.getReturnType();
            Type genericReturnType =  currentMethod.getGenericReturnType();

            if(genericReturnType instanceof ParameterizedType){
               ((ParameterizedType) genericReturnType).getActualTypeArguments();
            }
            RedisCache redisCache = currentMethod.getAnnotation(RedisCache.class);
        } catch (Exception e) {
        }
        return result;
    }

}
