package com.cskaoyan.bean.wx.order;

import com.cskaoyan.bean.Goods;

import java.util.List;

@lombok.Data
public class Data {
    //状态码
    Integer orderStatus;

    //订单状态描述, 由状态码决定
    String orderStatusText;

    //是否参团
    boolean isGroupin;

    //订单号
    String orderSn;

    //实际支付价格
    Double actualPrice;

    //商品list
    List<Goods> goodsList;

    //订单表
    Integer id;


    HandleOption handleOption;
}
