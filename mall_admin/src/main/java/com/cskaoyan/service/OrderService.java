package com.cskaoyan.service;


import com.cskaoyan.bean.Order;

import java.util.List;

public interface OrderService {
    List<Order> queryOrder(Integer showType, Integer page, Integer size, Integer id);
}
