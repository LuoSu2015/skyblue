package com.cskaoyan.service;

import com.cskaoyan.bean.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsService {

    List<Goods> getGoodsList(String sort, String order,Integer goodsSn,String name);
}
