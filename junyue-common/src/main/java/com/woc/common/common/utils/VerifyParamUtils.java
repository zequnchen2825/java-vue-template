package com.woc.common.common.utils;


import com.woc.common.common.RespContants;
import com.woc.common.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class VerifyParamUtils {

    public static void verifyParam(Object... params)  {
        for (Object param : params) {

            if (param instanceof String) {
                if (StringUtils.isEmpty((String) param)) throw new BusinessException(RespContants.PARAMS_ISNOT_RIGHT);

            } else {
                if (param == null) throw new BusinessException(RespContants.PARAMS_ISNOT_RIGHT);
            }
        }

    }

    public static void verifyParam(Object param ,String paramName) throws Exception {

        if (param instanceof String) {
            if (StringUtils.isEmpty((String) param)) throw new BusinessException(RespContants.PARAMS_ISNOT_RIGHT,",参数["+paramName+"]为空");
        } else {
            if (param == null) throw new BusinessException(RespContants.PARAMS_ISNOT_RIGHT,",参数["+paramName+"]为空");
        }
    }


    public static void verifyParam(Map<String,Object> params) throws Exception {

        for(String key : params.keySet()){
            Object param = params.get(key);
            if (param instanceof String) {
                if (StringUtils.isEmpty((String) param)) throw new BusinessException(RespContants.PARAMS_ISNOT_RIGHT,",参数["+key+"]为空");

            } else {
                if (param == null) throw new BusinessException(RespContants.PARAMS_ISNOT_RIGHT,",参数["+key+"]为空");
            }
        }


    }

    public static void verifyAccount(String account) throws Exception {
        if (StringUtils.isEmpty(account)) {
            throw new BusinessException(RespContants.USER_ACCOUNT_IS_NULL);
        }
        if (account.length() < 7 || account.length() > 15) {
            throw new BusinessException(RespContants.USER_LENGTH_ERRER);
        }
        if (!((account.charAt(0) >= 65 && account.charAt(0) <= 90) || (account.charAt(0) >= 97 && account.charAt(0) <= 112))) {
            throw new BusinessException(RespContants.USER_START_ISNOT_ALPHABET);
        }
        for (char c : account.substring(1).toCharArray()) {
            if (c >= 64 && c <= 90) {
                continue;
            }
            if (c >= 97 || c <= 122) {
                continue;
            }

            throw new BusinessException(RespContants.USER_ILLAGE);
        }

    }

    public static void verifyPassword(String password) throws Exception {
        if (StringUtils.isEmpty(password)) {
            throw new BusinessException(RespContants.USER_PASSWORD_IS_NULL);
        }
        if (password.length() < 7 || password.length() > 15) {
            throw new BusinessException(RespContants.USER_PASSWORD_LENGTH_ERRER);
        }


    }

    public static boolean verifyIsNull(Object... params) throws Exception {

        for (Object param : params) {
            verifyIsNull(param,RespContants.PARAMS_ISNOT_RIGHT);
        }
        return false;
    }

    public static boolean verifyIsNull(Object param, RespContants respContants) throws Exception {


        if (param == null) {
            throw new BusinessException(respContants);
        }

        return false;
    }


    public static boolean verifyIsNotNull(Object... params) {

        for (Object param : params) {
            if (param == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean verifyAllNull(Object... params){
        for (Object param : params) {
            if (param != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 类型校验
     * @param type
     * @param list
     * @return
     */
    public static boolean verifyType(Object type , List<Object> list) {

        return !list.contains(type);
    }

    /**
     * 验证学年是否合法
     * @param year
     * @param joinYear
     * @return
     */
    public static boolean verifyYear(String year,String joinYear){
        int intYear = Integer.parseInt(year);
        int intJoinYear = Integer.parseInt(joinYear);
        int year1 = LocalDateTime.now().getYear();

        if(intYear < intJoinYear || intYear > year1){
            return true;
        }
//        if(){
//
//        }

        return false;
    }

//    /**
//     * 验证xueqishif合法
//     * @param term
//     * @return
//     */
//    public static boolean verifyTerm(String term){
//        verifyType(term, Arrays.asList("01","02"));
//        int monthValue = LocalDateTime.now().getMonthValue();
//
//        if(monthValue >=9 && monthValue<=2){
//            return "01".equals(term);
//        }else if(monthValue >= 2 && monthValue < 9){
//            return "02".equals(term);
//        }
//        return false;
//    }
}
