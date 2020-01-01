package com.cskaoyan.controller;

import com.cskaoyan.bean.*;
import com.cskaoyan.service.WxGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WxHomeController {
    @Autowired
    WxGoodsService wxGoodsService;

    @RequestMapping("wx/home/index")
    public BaseRespVo showHome(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();

        List<Ad> banner = wxGoodsService.getBannerList();
        List<Coupon> couponList = wxGoodsService.getCouponList();
        List<Category> channel = wxGoodsService.getChannelList();
        List<Brand> brandList = wxGoodsService.getBrandList();
        List<Goods> newGoodsList = wxGoodsService.getNewGoodsList();
        List<Goods> hotGoodsList = wxGoodsService.getHotGoodsList();

        data.put("banner",banner);
        data.put("couponList",couponList);
        data.put("channel",channel);
        data.put("brandList",brandList);
        data.put("newGoodsList",newGoodsList);
        data.put("hotGoodsList",hotGoodsList);

        baseRespVo.setErrno(0);
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("wx/goods/count")
    public BaseRespVo goodsCount(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String,Integer> data = new HashMap<>();

        Integer goodsCount = wxGoodsService.getGoodsCount();
        data.put("goodsCount",goodsCount);

        baseRespVo.setErrno(0);
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
