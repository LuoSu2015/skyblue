package com.cskaoyan.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 微信小程序端团购专区首页显示类
 */
@Data
public class WxGrouponList {
    private Integer id;

    private String goods_sn;

    private String name;

    private Integer category_id;

    private Integer brand_id;

    private String[] gallery;

    private String keywords;

    private String brief;

    private Boolean is_on_sale;

    private Short sort_order;

    private String picUrl;

    private String share_url;

    private Boolean is_new;

    private Boolean is_hot;

    private String unit;

    private BigDecimal counterPrice;

    private BigDecimal retailPrice;

    private Date add_time;

    private Date update_time;

    private Boolean deleted;

    private Integer goods_id;

    private String goods_name;

    private BigDecimal discount;

    private Integer discount_member;

}
