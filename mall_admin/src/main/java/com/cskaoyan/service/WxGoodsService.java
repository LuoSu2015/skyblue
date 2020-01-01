package com.cskaoyan.service;

import com.cskaoyan.bean.*;

import java.util.List;

public interface WxGoodsService {
    Integer getGoodsCount();

    List<Ad> getBannerList();

    List<Coupon> getCouponList();

    List<Category> getChannelList();

    List<Brand> getBrandList();

    List<Goods> getNewGoodsList();

    List<Goods> getHotGoodsList();

}
