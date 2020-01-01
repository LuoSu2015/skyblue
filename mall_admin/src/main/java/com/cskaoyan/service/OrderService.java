package com.cskaoyan.service;


import java.util.Map;

public interface OrderService {
    Map queryOrder(Integer showType, Integer page, Integer size, Integer id);

    Map queryOrderDetail(Integer orderId);
}
