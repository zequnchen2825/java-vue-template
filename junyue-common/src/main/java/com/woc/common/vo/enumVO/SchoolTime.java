package com.woc.common.vo.enumVO;



public enum SchoolTime {

    ONE_YEAR_ONE("大一第一学期","11"),
    ONE_YEAR_TWO("大一第二学期","12"),
    TWO_YEAR_ONE("大二第一学期","21"),
    TWO_YEAR_TWO("大二第二学期","22"),
    THREE_YEAR_ONE("大三第一学期","31"),
    THREE_YEAR_TWO("大三第二学期","32"),
    FOUR_YEAR_ONE("大四第一学期","41"),
    FOUR_YEAR_TWO("大四第二学期","42"),
    FIVE_YEAR_ONE("大五第一学期","51"),
    FIVE_YEAR_TWO("大五第二学期","52")
    ;

    private String value;

    private String time;

    SchoolTime(String value ,String time){
        this.value = value;
        this.time = time;
    }



    public static String getValue(String time){
        SchoolTime[] schoolTimes = values();
        for(SchoolTime schoolTime : schoolTimes){
            if(schoolTime.time.equals(time)){
                return schoolTime.value;
            }
        }
        return  null;
    }


}
