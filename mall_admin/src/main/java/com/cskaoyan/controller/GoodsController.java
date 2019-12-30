package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Goods;
import com.cskaoyan.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @RequestMapping("admin/goods/list")
    public BaseRespVo goodsList(int page,int limit,String sort,String order,Integer goodsSn,String name){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();

        PageHelper.startPage(page,limit);
        List<Goods> items = goodsService.getGoodsList(sort,order,goodsSn,name);
        PageInfo<Goods> pageInfo = new PageInfo(items);
        long total = pageInfo.getTotal();

        data.put("items",items);
        data.put("total",total);

        baseRespVo.setData(data);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("admin/goods/catAndBrand")
    public BaseRespVo catAndBrand(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();

        List<Object> categoryList;

        baseRespVo.setData(data);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
