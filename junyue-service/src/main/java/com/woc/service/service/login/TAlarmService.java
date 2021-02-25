package com.woc.service.service.login;

import com.woc.common.entity.TAlarm;

public interface TAlarmService {
    int saveOrUpdateAlarm(TAlarm tAlarm);

    TAlarm findAlarmByUserId(Integer id);
}
