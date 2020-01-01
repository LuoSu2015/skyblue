package com.cskaoyan.service;


import java.util.Map;

public interface OrderService {
    Map queryOrder(Integer showType, Integer page, Integer size, Integer id);

    Map queryOrderDetail(Integer orderId);

    boolean createOrder(Integer cartId, Integer addressId, Integer couponId, String message, Integer grouponRulesId, Integer grouponLinkId);
}
