package com.woc.service.service.user;

import com.woc.common.common.RespContants;
import com.woc.common.entity.WocUser;
import com.woc.common.vo.LoginVO;
import com.woc.common.vo.MenuVO;

import java.util.List;

public interface ICmsLoginService {
    boolean login(WocUser wocUser) throws Exception;

    Boolean logout();

    LoginVO getInfo();

}
