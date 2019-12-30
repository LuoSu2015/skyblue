package com.cskaoyan.bean.goods;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class AttributeDetail {
    private Integer id;
    private Integer goodsId;
    private String attribute;
    private String value;
    private Date addTime;
    private Date updateTime;
    private boolean deleted;
}
