package com.cskaoyan.service;

import com.cskaoyan.bean.Goods;
import com.cskaoyan.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService{

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public List<Goods> getGoodsList(String sort,String order,Integer goodsSn,String name) {
        List<Goods> items = goodsMapper.selectAllGoods(sort,order,goodsSn,name);
        return items;
    }
}
