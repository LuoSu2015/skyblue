package com.cskaoyan.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class WxUserInfo {
    private String avatarUrl;
    private String nickName;
}
