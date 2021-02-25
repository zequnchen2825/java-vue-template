package com.woc.service.service.common;

import com.woc.common.common.bean.RespResult;
import com.woc.common.entity.TUser;


public interface CommonService {
    boolean getAllCourse(TUser tUser, String time) throws Exception;

    RespResult changeJc();
}
