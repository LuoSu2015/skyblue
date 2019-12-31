package com.cskaoyan.service;


import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    GoodsProductMapper goodsProductMapper;
    @Autowired
    OrderMapper orderMapper;

    /**
     * 首页显示功能
     * @return 首页数据统计
     */
    @Override
    public Map dashboard() {
        GoodsExample goodsExample = new GoodsExample();
        UserExample userExample = new UserExample();
        GoodsProductExample goodsProductExample = new GoodsProductExample();
        OrderExample orderExample = new OrderExample();
        Map map = new HashMap();
        long goods = goodsMapper.countByExample(goodsExample);
        long users = userMapper.countByExample(userExample);
        long products = goodsProductMapper.countByExample(goodsProductExample);
        long orders = orderMapper.countByExample(orderExample);
        map.put("goodsTotal",goods);
        map.put("userTotal",users);
        map.put("productTotal",products);
        map.put("orderTotal",orders);
        return map;
    }
}
