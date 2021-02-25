package com.woc.common.entity.extend;

import com.woc.common.entity.WocPlatform;
import com.woc.common.entity.WocUser;

public class WocUserExtend extends WocUser {

    private WocPlatform platform;

    public WocPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(WocPlatform platform) {
        this.platform = platform;
    }
}
