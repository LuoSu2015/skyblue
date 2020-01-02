package com.cskaoyan.bean.wx.order;

import lombok.Data;

@Data
public class OrderGoodsReturn {
    private String picUrl;
    private String goodsName;
    private Short number;
    private String goodsSpecificationValues;
}
