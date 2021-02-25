package com.woc.common.entity.ignore;


import com.woc.common.entity.WocUser;

public class WocUserIgnore extends WocUser {

    @Override
    public void setSalt(String salt) {
        super.setSalt(null);
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(null);
    }
}
