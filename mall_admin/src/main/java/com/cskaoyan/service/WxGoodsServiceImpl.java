package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("wxGoodsService")
public class WxGoodsServiceImpl implements WxGoodsService{

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public Integer getGoodsCount() {
        List<Goods> goodsList = goodsMapper.selectAllGoods("add_time", "desc", null, null);
        Integer size = goodsList.size();
        return size;
    }

    @Override
    public List<Ad> getBannerList() {
        List<Ad> banner = goodsMapper.getBannerList();
        return banner;
    }

    @Override
    public List<Coupon> getCouponList() {
        List<Coupon> couponList = goodsMapper.getCouponList();
        return couponList;
    }

    @Override
    public List<Category> getChannelList() {
        List<Category> channel = goodsMapper.getChannelList();
        return channel;
    }

    @Override
    public List<Brand> getBrandList() {
        List<Brand> brandList = goodsMapper.getBrandList();
        return brandList;
    }

    @Override
    public List<Goods> getNewGoodsList() {
        List<Goods> newGoodsList = goodsMapper.getNewGoodsList();
        return newGoodsList;
    }

    @Override
    public List<Goods> getHotGoodsList() {
        List<Goods> hotGoodsList = goodsMapper.getHotGoodsList();
        return hotGoodsList;
    }
}
