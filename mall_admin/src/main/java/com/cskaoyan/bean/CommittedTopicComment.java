package com.cskaoyan.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class CommittedTopicComment {
    private Integer type;
    private String valueId;
    private String content;
    private Integer star;
    private boolean hasPicture;
    private String[] picUrls;
    private Date addTime;
    private Integer id;
    private Date updateTime;
    private Integer userId;
}
