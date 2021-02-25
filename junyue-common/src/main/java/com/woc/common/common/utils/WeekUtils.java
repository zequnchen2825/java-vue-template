package com.woc.common.common.utils;

import java.util.List;

public class WeekUtils {

    public static String findMintoMax(List<String> weeks) {

        int max = 0;
        int min = 0;
        for (String week : weeks) {
            int weekInt = Integer.parseInt(week);
            max = Math.max(max,weekInt);
            min = Math.min(min,weekInt);
        }
        return "第"+min+"-"+max+"周";
    }
}
