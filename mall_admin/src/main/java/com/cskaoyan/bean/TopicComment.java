package com.cskaoyan.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class TopicComment {
    private Date addTime;
    private String content;
    private String[] picList;
    private WxUserInfo userInfo;
}
