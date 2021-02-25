package com.woc.common.common.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UserToken extends UsernamePasswordToken {


    public UserToken(String account,String password){

        setUsername(account);
        setPassword(password.toCharArray());

    }

}
