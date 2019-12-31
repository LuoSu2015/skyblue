package com.cskaoyan.service.cart;

import com.cskaoyan.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public List queryGoodsList() {
        return null;
    }

    @Override
    public Object queryCartStatus() {
        return null;
    }
}
