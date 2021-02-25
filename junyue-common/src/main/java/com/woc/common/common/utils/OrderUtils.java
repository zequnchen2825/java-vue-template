package com.woc.common.common.utils;

import java.text.NumberFormat;

public class OrderUtils {

    private static RedisUtils redisUtils;
    static{
        redisUtils = SpringUtil.getBean(RedisUtils.class);
    }

    public static String createOrderId(long count) throws Exception {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(6);
        formatter.setGroupingUsed(false);
        return DateUtils.getDateLong()+formatter.format(count);
    }
}
