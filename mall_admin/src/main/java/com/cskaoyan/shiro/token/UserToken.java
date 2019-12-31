package com.cskaoyan.shiro.token;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

@Data
public class UserToken extends UsernamePasswordToken {

    private String type;

    /*这种不是随便设置的，必须要将username和password传过去*/
    public UserToken(String username, String password, String type) {
        super(username, password);
        this.type = type;
    }
}
