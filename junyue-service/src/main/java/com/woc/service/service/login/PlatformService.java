package com.woc.service.service.login;


import com.woc.common.common.bean.PRespResult;
import com.woc.common.common.bean.RespResult;
import com.woc.common.entity.WocPlatform;

public interface PlatformService {

    public WocPlatform selectPlatformByTag(String platformTag) throws Exception;

    PRespResult pagelist(Integer page, Integer pageSize);

    Boolean update(WocPlatform wocPlatform);

    WocPlatform findById(Integer platformId) throws Exception;

    Boolean insert(WocPlatform wocPlatform);
}
