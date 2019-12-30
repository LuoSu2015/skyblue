package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Comment;
import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.Storage;
import com.cskaoyan.bean.goods.*;
import com.cskaoyan.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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


    @RequestMapping("admin/comment/list")
    public BaseRespVo commentList(int page,int limit,String sort,String order,Integer userId,Integer valueId){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();

        PageHelper.startPage(page,limit);
        List<Comment> items = goodsService.getCommentList(sort,order,userId,valueId);
        PageInfo<Comment> pageInfo = new PageInfo(items);
        long total = pageInfo.getTotal();

        data.put("items",items);
        data.put("total",total);

        baseRespVo.setData(data);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("admin/comment/delete")
    public BaseRespVo deleteComment(@RequestBody Comment comment){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();

        goodsService.deleteComment(comment);

        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("admin/goods/catAndBrand")
    public BaseRespVo catAndBrand(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();

        List<MyCategory> categoryList = goodsService.getCategoryList();
        List<MyBrand> brandList = goodsService.getBrandList();
        data.put("categoryList",categoryList);
        data.put("brandList",brandList);

        baseRespVo.setData(data);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("admin/goods/create")
    public BaseRespVo addGoods(@RequestBody MyGoods goods){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();

        goodsService.addGoods(goods);

        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("admin/goods/delete")
    public BaseRespVo deleteGoods(@RequestBody DeleteGoods goods){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();

        goodsService.deleteGoods(goods);

        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("admin/goods/detail")
    public BaseRespVo detailGoods(@RequestParam Integer id){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();

        List<Integer> categoryIds = goodsService.getCategoryIdsOfGoods(id);
        //isNew isHot 等判断boolean类型的未在返回json中显示？
        DetailGoods goods = goodsService.getDetailOfGoods(id);
        List<AttributeDetail> attributes = goodsService.getDetailOfAttributes(id);
        List<SpecificationDetail>specifications = goodsService.getDetailOfSpecifications(id);
        List<ProductDetail> products = goodsService.getDetailOfProducts(id);

        data.put("categoryIds",categoryIds);
        data.put("goods",goods);
        data.put("attributes",attributes);
        data.put("specifications",specifications);
        data.put("products",products);

        baseRespVo.setErrno(0);
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }


    @RequestMapping("admin/goods/update")
    public BaseRespVo updateGoods(@RequestBody MyGoods goods){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();

        goodsService.updateGoods(goods);

        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("admin/order/reply")
    public BaseRespVo replyGoods(@RequestBody GoodsReply goodsReply){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();

        //回复后将该条商品评论逻辑删除
        goodsService.replyComment(goodsReply);

        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

}
