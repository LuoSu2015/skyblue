package com.cskaoyan.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Component
public class WxTopic {
    private Integer id;

    private String title;

    private String subtitle;

    private BigDecimal price;

    private String readCount;

    private String picUrl;

    private Integer sortOrder;

    private List<Goods> goods;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

    private String content;
}
