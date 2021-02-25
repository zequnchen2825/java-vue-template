package com.woc.common.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {



    public static String getWyuTimeNow(){
        LocalDateTime localDateTime = LocalDateTime.now();
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        //第二学期
        if(month>2 && month < 9 ){
            year -= 1;
            return year+"02";
        }else{  //第一学期
            return year+"01";
        }
    }

    public static String getDateLong() throws Exception{
        LocalDate localDate = LocalDate.now();
        return localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * 判断当前时间距离第二天凌晨的秒数
     *
     * @return 返回值单位为[s:秒]
     */
    public static Long getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

    public static Date parse(String time,String pattern) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.parse(time);
    }
    public static String format(Date time,String pattern) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.format(time);
    }

    public static Date getEarlyMorning() throws Exception {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime now = LocalDateTime.now();
        return Date.from(LocalDateTime.of(now.toLocalDate(), LocalTime.MIN).atZone(zoneId).toInstant());
    }

    public static Date addDate(Date date, int count, TimeUnit timeUnit) {
        long millis = timeUnit.toMillis(count);
        Date addDate = new Date(date.getTime() + millis);
        return addDate;
    }
}
