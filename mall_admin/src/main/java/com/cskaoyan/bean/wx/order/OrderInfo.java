package com.cskaoyan.bean.wx.order;

import lombok.Data;

import java.util.Date;

@Data
public class OrderInfo {

    String consignee;

    String address;

    Date addTime;

    String orderSn;

    Double actualPrice;

    String mobile;

    String orderStatusText;

    Double goodsPrice;

    Double couponPrice;

    Integer id;

    Double freightPrice;

    HandleOption handleOption;
}
