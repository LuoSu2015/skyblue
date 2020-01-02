package com.cskaoyan.service;


import com.cskaoyan.bean.Comment;
import com.cskaoyan.bean.Order;
import com.cskaoyan.bean.OrderGoods;
import com.cskaoyan.bean.User;
import com.cskaoyan.bean.wx.OrderStatus;
import com.cskaoyan.bean.wx.order.OrderGoodsReturn;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.util.Map;

public interface OrderService {
    Map queryOrder(Integer showType, Integer page, Integer size, Integer id);

    Map queryOrderDetail(Integer orderId);


    Integer createOrder(User user);

    int deleteOrderById(Integer orderId);

    int orderConfirm(int id);

    Order queryOrderById(Integer id);

    int payOrders(Integer id);

    OrderStatus selectOrderStatus(User user);

    OrderGoodsReturn selectOrderGoodsReturn(Integer goodsId,Integer orderId);

    int insertComment(Comment comment,User user);
}
