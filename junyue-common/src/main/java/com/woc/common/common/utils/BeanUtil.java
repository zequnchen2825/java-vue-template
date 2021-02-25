package com.woc.common.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class BeanUtil {

    public static Object ignore(Object obj,Class<?> clazz,String... filedNames){
        if(obj instanceof List){
            List<Object> list = (List<Object>) obj;
            for(Object object : list){

                for(String filedName : filedNames){
                    setNullValue(object,filedName,clazz);
                }
            }
        }else{
            for(String filedName : filedNames){
                setNullValue(obj,filedName,clazz);
            }
        }
        return obj;
    }
    public static Object keep(Object obj,Class<?> clazz,String... filedNames) throws Exception{
        List<String> fileds = Arrays.asList(filedNames);

        Field[] declaredFields = clazz.getDeclaredFields();
        if(obj instanceof List){
            List<Object> list = (List<Object>) obj;
            for(Object object : list){
                for(Field field : declaredFields){
                    if("serialVersionUID".equals(field.getName())){
                        continue;
                    }
                    if(!fileds.contains(field.getName())){
                        ignore(object,clazz,field.getName());
                    }
                }

            }
        }else{
            for(Field field : declaredFields){
                if("serialVersionUID".equals(field.getName())){
                    continue;
                }
                if(!fileds.contains(field.getName())){
                    ignore(obj,clazz,field.getName());
                }
            }
        }
        return obj;
    }

    private static void setNullValue(Object object,String filedName,Class<?> clazz ){
        String methodName = "set" + filedName.substring(0,1).toUpperCase()+filedName.substring(1);
        try{
            Class<?> type = clazz.getDeclaredField(filedName).getType();
            Method method =  clazz.getDeclaredMethod(methodName, new Class[]{type});
            method.invoke(object, new Object[]{null});

        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    /**
     * 通过class类型获取获取对应类型的值
     * @param typeClass class类型
     * @param value 值
     * @return Object
     */
    private static Object getClassTypeValue(Class<?> typeClass, Object value){
        if(typeClass == int.class  || value instanceof Integer){
            if(null == value){
                return 0;
            }
            return value;
        }else if(typeClass == short.class){
            if(null == value){
                return 0;
            }
            return value;
        }else if(typeClass == byte.class){
            if(null == value){
                return 0;
            }
            return value;
        }else if(typeClass == double.class){
            if(null == value){
                return 0;
            }
            return value;
        }else if(typeClass == long.class){
            if(null == value){
                return 0;
            }
            return value;
        }else if(typeClass == String.class){
            if(null == value){
                return null;
            }
            return value;
        }else if(typeClass == boolean.class){
            if(null == value){
                return true;
            }
            return value;
        }else if(typeClass == BigDecimal.class){
            if(null == value){
                return new BigDecimal(0);
            }
            return new BigDecimal(value+"");
        }else {
            return typeClass.cast(value);
        }
    }

}
