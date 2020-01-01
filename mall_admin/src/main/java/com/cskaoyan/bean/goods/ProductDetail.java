package com.cskaoyan.bean.goods;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Component
public class ProductDetail {
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
