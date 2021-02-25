package com.woc.common.common.utils;

import java.math.BigDecimal;

/**
 * @Date 2019-05-20 18:55
 * @Version 1.0
 * @Description 计算工具
 */
public class MathUtil {


    public static Double divideRoundUp(double d1, double d2, int scale) {
        return divide(d1, d2, scale, BigDecimal.ROUND_UP);
    }

    public static Double divideRoundDown(double d1, double d2, int scale) {
        return divide(d1, d2, scale, BigDecimal.ROUND_DOWN);
    }

    public static Double divideRoundHalfUp(double d1, double d2, int scale) {
        return divide(d1, d2, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static Double divideRoundHalfDown(double d1, double d2, int scale) {
        return divide(d1, d2, scale, BigDecimal.ROUND_HALF_DOWN);
    }

    public static Double divideRoundCeiling(double d1, double d2, int scale) {
        return divide(d1, d2, scale, BigDecimal.ROUND_CEILING);
    }

    public static Double divideRoundFloor(double d1, double d2, int scale) {
        return divide(d1, d2, scale, BigDecimal.ROUND_FLOOR);
    }

    public static Double divideRoundHalfEven(double d1, double d2, int scale) {
        return divide(d1, d2, scale, BigDecimal.ROUND_HALF_EVEN);
    }

    public static Double divideRoundUnnecessary(double d1, double d2, int scale) {
        return divide(d1, d2, scale, BigDecimal.ROUND_UNNECESSARY);
    }

    public static Double divide(double d1, double d2, int scale, int roundingMode) {
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);

        return b1.divide(b2, scale, roundingMode).doubleValue();
    }

    public static Double add(double d1, double d2) {
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);

        return b1.add(b2).doubleValue();
    }
    public static Double subtract(double d1, double d2) {
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);

        return b1.subtract(b2).doubleValue();
    }
    public static Double subtract(double d1, double... d2s) {
        BigDecimal b1 = BigDecimal.valueOf(d1);

        for(double d2 :d2s){
            BigDecimal b2 = BigDecimal.valueOf(d2);
            b1 = b1.subtract(b2);
        }

        return b1.doubleValue();
    }

    public static Double multiply(double d1, double d2) {
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);

        return b1.multiply(b2).doubleValue();
    }

}
