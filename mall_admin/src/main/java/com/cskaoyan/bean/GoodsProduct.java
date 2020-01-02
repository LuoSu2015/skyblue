package com.cskaoyan.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class GoodsProduct {
    private Integer id;

    private Integer goodsId;

    private String[] specifications;

    private BigDecimal price;

    private Integer number;

    private String url;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;


}
