package com.cskaoyan.bean.goods;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Component
public class DeleteGoods {
    private Integer id;
    private String goodsSn;
    private String name;
    private Integer categoryId;
    private Integer brandId;
    private String[] gallery;
    private String keywords;
    private String brief;
    private boolean isOnSale;
    private Integer sortOrder;
    private String picUrl;
    private String shareUrl;
    private boolean isNew;
    private boolean isHot;
    private String unit;
    private String counterPrice;
    private String retailPrice;
    private Date addTime;
    private Date updateTime;
    private boolean deleted;
    private String detail;

}
