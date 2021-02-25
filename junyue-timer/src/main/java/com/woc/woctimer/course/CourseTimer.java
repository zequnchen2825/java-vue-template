package com.woc.woctimer.course;

import com.woc.service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CourseTimer {

    @Autowired
    private UserService userService;
    /**
     * 每一个小时执行一次提醒
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void courseAlarmTimer(){
        userService.alarmCourse();
    }
}
