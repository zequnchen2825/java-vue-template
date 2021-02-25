package com.woc.dao.cache;

import java.util.concurrent.TimeUnit;

public enum RedisTimeEnum {
    ONE_DAY(TimeUnit.DAYS.toMillis(1));

    private Long time;

    RedisTimeEnum(Long time) {
        this.time = time;
    }
}
