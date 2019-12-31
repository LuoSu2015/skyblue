package com.cskaoyan.service;

import com.cskaoyan.bean.Comment;
import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.goods.*;

import java.util.List;
import java.util.Map;

public interface GoodsService {

    List<Goods> getGoodsList(String sort, String order,Integer goodsSn,String name);

    List<MyCategory> getCategoryList();

    List<MyBrand> getBrandList();

    void addGoods(MyGoods goods);

    void deleteGoods(DeleteGoods goods);

    List<Integer> getCategoryIdsOfGoods(Integer id);

    DetailGoods getDetailOfGoods(Integer id);

    List<AttributeDetail> getDetailOfAttributes(Integer id);

    List<SpecificationDetail> getDetailOfSpecifications(Integer id);

    List<ProductDetail> getDetailOfProducts(Integer id);

    void updateGoods(MyGoods goods);

    List<Comment> getCommentList(String sort, String order,Integer userId,Integer valueId);

    void deleteComment(Comment comment);

    void replyComment(GoodsReply goodsReply);
}
