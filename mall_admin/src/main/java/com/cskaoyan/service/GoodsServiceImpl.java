package com.cskaoyan.service;

import com.cskaoyan.bean.Comment;
import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.goods.*;
import com.cskaoyan.mapper.BrandMapper;
import com.cskaoyan.mapper.CategoryMapper;
import com.cskaoyan.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService{

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public List<Goods> getGoodsList(String sort,String order,Integer goodsSn,String name) {
        List<Goods> items = goodsMapper.selectAllGoods(sort,order,goodsSn,name);
        return items;
    }

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<MyCategory> getCategoryList() {
        return categoryMapper.selectAllCategory();
    }

    @Autowired
    BrandMapper brandMapper;

    @Override
    public List<MyBrand> getBrandList() {
        return brandMapper.selectAllBrand();
    }

    @Override
    public void addGoods(MyGoods goods) {
        MyAddGoods myAddGoods = goods.getGoods();
        List<AddGoodsSpecification> addGoodsSpecifications = goods.getSpecifications();
        List<AddProducts> products = goods.getProducts();
        List<AddAttribute> attributes = goods.getAttributes();

//        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String adddate = sd.format(new Date());
        Date date = new Date();
        myAddGoods.setAddtime(date);
        myAddGoods.setUpdatetime(date);

        int goods_id = Integer.parseInt(myAddGoods.getGoodsSn());
        myAddGoods.setId(goods_id);
        goodsMapper.addGoods(myAddGoods);
        goodsMapper.addGoodsSpecification(goods_id,addGoodsSpecifications);
        goodsMapper.addProducts(goods_id,products);
        goodsMapper.addAttribute(goods_id,attributes);
    }

    @Override
    public void deleteGoods(DeleteGoods goods) {
        int goodId = goods.getId();
        goodsMapper.deleteObjectOfGoods(goodId);
        goodsMapper.deleteSpecificationsOfGoods(goodId);
        goodsMapper.deleteProductsOfGoods(goodId);
        goodsMapper.deleteAttributeOfGoods(goodId);
    }

    @Override
    public List<Integer> getCategoryIdsOfGoods(Integer id) {
        Integer l2_id = goodsMapper.getL2CategoryIdByGoodsId(id);
        Integer l1_id = goodsMapper.getL1CategoryIdByL2Id(l2_id);
        List<Integer> cateIdList = new ArrayList<>();
        cateIdList.add(0,l1_id);
        cateIdList.add(1,l2_id);
        return cateIdList;
    }

    @Override
    public DetailGoods getDetailOfGoods(Integer id) {
        DetailGoods detailGoods = goodsMapper.getDetailOfGoods(id);
        return detailGoods;
    }

    @Override
    public List<AttributeDetail> getDetailOfAttributes(Integer id) {
        List<AttributeDetail> attributes = goodsMapper.getDetailOfAttributes(id);
        return attributes;
    }

    @Override
    public List<SpecificationDetail> getDetailOfSpecifications(Integer id) {
        List<SpecificationDetail>specifications = goodsMapper.getDetailOfSpecifications(id);
        return specifications;
    }

    @Override
    public List<ProductDetail> getDetailOfProducts(Integer id) {
        List<ProductDetail> products = goodsMapper.getDetailOfProducts(id);
        return products;
    }

    @Override
    public void updateGoods(MyGoods goods) {
        //id未修改，修改的是goodsSn
        int goodsId = goods.getGoods().getId();

        goodsMapper.updateObjectOfGoods(goods.getGoods());

        //update Spec :前台对于被删除的规格或属性，并不会以deleted = true 的形式传过来，而是直接不传
        //做法：in (该商品下全部规格的ids) and not in (前台传过来的全部ids) --> deleted 置为 true
        List<AddGoodsSpecification> newSpecsList = new ArrayList<>();
        List<AddGoodsSpecification> undeletedSpecsList = goods.getSpecifications();
        for (AddGoodsSpecification specification : goods.getSpecifications()) {
            if (specification.getId() == null){
                newSpecsList.add(specification);
            }
        }
        //先不添加新的规格或属性，先将之前旧的根据goodsId删除原范围的内需要删除的规格，再添加新的规格
        boolean allIdNull = true;
        for (AddGoodsSpecification addGoodsSpecification : undeletedSpecsList) {
            if (addGoodsSpecification.getId() != null){
                allIdNull = false;
            }
        }
        if (undeletedSpecsList.size() != 0 && !allIdNull){
            goodsMapper.deleteGoodsSpecifications(goodsId,undeletedSpecsList);
        }
        if (newSpecsList.size() != 0){
            goodsMapper.addGoodsSpecification(goodsId,newSpecsList);
        }


        //根据goodsId和spec的组合来唯一定位，暂只允许修改价格、数量、图片这三个参数
        goodsMapper.deleteProductsByGoodsId(goodsId);
        goodsMapper.addProductsByGoodsId(goodsId,goods.getProducts());

        //update attributes
        List<AddAttribute> newAttributesList = new ArrayList<>();
        List<AddAttribute> undeletedAttributesList = goods.getAttributes();
        for (AddAttribute attribute : goods.getAttributes()) {
            if (attribute.getId() == null){
                newAttributesList.add(attribute);
            }
        }

        allIdNull = true;
        for (AddAttribute undeletedAttribute: undeletedAttributesList) {
            if (undeletedAttribute.getId() != null){
                allIdNull = false;
            }
        }
        if (undeletedAttributesList.size() != 0 && !allIdNull){
            goodsMapper.deleteAttributes(goodsId,undeletedAttributesList);
        }
        if (newAttributesList.size() != 0){
            goodsMapper.addAttribute(goodsId,newAttributesList);
        }
    }

    @Override
    public List<Comment> getCommentList(String sort, String order,Integer userId,Integer valueId) {
        List<Comment> commentList = goodsMapper.getCommentList(sort,order,userId,valueId);
        return commentList;
    }

    @Override
    public void deleteComment(Comment comment) {
        goodsMapper.deleteComment(comment);
    }

    @Override
    public void replyComment(GoodsReply goodsReply) {
        goodsMapper.addReply(goodsReply);
        Comment deletedcomment = goodsMapper.getCommentById(goodsReply.getCommentId());
        goodsMapper.deleteComment(deletedcomment);
    }
}
