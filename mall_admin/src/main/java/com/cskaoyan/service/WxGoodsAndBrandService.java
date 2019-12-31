package com.cskaoyan.service;

import com.cskaoyan.bean.*;

import java.util.List;

public interface WxGoodsAndBrandService {

    Brand slectBrandById(int id);

    List<Goods> selectGoodsWithMulty(Integer brandId,Integer page,Integer size,String order,String sort,Boolean isHot,Integer categoryId,Boolean isNew);

    Long countGoods();

    Category selectCategoryById(Integer id);

    List<Category> selectCategoryByPid(Integer pid);
    //获得规格
    List<GoodsSpecification> selectSpecificationByGoodsId(Integer goodsId);
   //根据规格获得详细信息
    List<GoodsSpecification> selectSpecificationByGoodsIsAndSpecification(Integer goodsId,String goodsSpecification);

    List<Issue> selectIssue();

    List<GoodsProduct> selectGoodsProductByGoodsId(Integer goodsId);

    List<GoodsAttribute> selectGoodsAttributeByGoodsId(Integer goodsId);

    Brand selectBrandByGoodsId(Integer brandId);

    Goods selectGoodsByGoodsId(Integer goodsId);


}
