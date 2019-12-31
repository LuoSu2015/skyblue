package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxGoodsAndBrandServiceImpl implements WxGoodsAndBrandService {
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    GoodsSpecificationMapper goodsSpecificationMapper;
    @Autowired
    IssueMapper issueMapper;
    @Autowired
    GoodsProductMapper goodsProductMapper;
    @Autowired
    GoodsAttributeMapper goodsAttributeMapper;


    @Override
    public Brand slectBrandById(int id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        return brand;
    }

    @Override
    public List<Goods> selectGoodsWithMulty(Integer brandId,Integer page,Integer size,String order,String sort,Boolean isHot,Integer categoryId,Boolean isNew) {
        GoodsExample goodsExample = new GoodsExample();
        //排序
        if(sort!=null && order!=null){
            goodsExample.setOrderByClause(sort + " " + order);
        }
        GoodsExample.Criteria criteria1 = goodsExample.createCriteria();
        if(brandId != null){
            criteria1.andBrandIdEqualTo(brandId);
        }
        if(isHot != null){
            criteria1.andIsHotEqualTo(isHot);
        }
        if(isNew != null){
            criteria1.andIsNewEqualTo(isNew);
        }
        if(categoryId != 0 && categoryId != null){
            criteria1.andCategoryIdEqualTo(categoryId);
        }

        //分页
        PageHelper.startPage(page,size);
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        return goodsList;

    }

    @Override
    public Long countGoods() {
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        long goodsCount = goodsMapper.countByExample(goodsExample);
        return goodsCount;
    }

    @Override
    public Category selectCategoryById(Integer id) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andIdEqualTo(id);
        Category category = categoryMapper.selectByPrimaryKey(id);
        return category;
    }

    @Override
    public List<Category> selectCategoryByPid(Integer pid) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andPidEqualTo(pid);
        criteria.andDeletedEqualTo(false);
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        return categories;
    }

    /**
     * 获得商品规格
     * @param goodsId
     * @return
     */
    @Override
    public List<GoodsSpecification> selectSpecificationByGoodsId(Integer goodsId) {
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectGroupBySpecificationByGoodId(goodsId);
        return goodsSpecifications;
    }

    /**
     * 获得商品详情
     * 根据规格获得详细信息
     * @param goodsId
     * @param goodsSpecification
     * @return
     */
    @Override
    public List<GoodsSpecification> selectSpecificationByGoodsIsAndSpecification(Integer goodsId, String goodsSpecification) {
        GoodsSpecificationExample goodsSpecificationExample = new GoodsSpecificationExample();
        GoodsSpecificationExample.Criteria criteria = goodsSpecificationExample.createCriteria();
        criteria.andSpecificationEqualTo(goodsSpecification);
        criteria.andGoodsIdEqualTo(goodsId);
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectByExample(goodsSpecificationExample);

        return goodsSpecifications;
    }

    /**
     *
     * 获得商品详情
     * @return
     */
    @Override
    public List<Issue> selectIssue() {
        IssueExample issueExample = new IssueExample();
        IssueExample.Criteria criteria = issueExample.createCriteria();

        List<Issue> issues = issueMapper.selectByExample(issueExample);
        return issues;
    }

    /**
     * 获得商品详情
     * @param goodsId
     * @return
     */
    @Override
    public List<GoodsProduct> selectGoodsProductByGoodsId(Integer goodsId) {
        GoodsProductExample goodsProductExample = new GoodsProductExample();
        GoodsProductExample.Criteria criteria = goodsProductExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        List<GoodsProduct> goodsProducts = goodsProductMapper.selectByExample(goodsProductExample);

        return goodsProducts;
    }

    @Override
    public List<GoodsAttribute> selectGoodsAttributeByGoodsId(Integer goodsId) {
        GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
        GoodsAttributeExample.Criteria criteria = goodsAttributeExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.selectByExample(goodsAttributeExample);

        return goodsAttributes;
    }

    @Override
    public Brand selectBrandByGoodsId(Integer brandId) {
        Brand brand = brandMapper.selectByPrimaryKey(brandId);
        return brand;
    }

    /**
     * 根据商品id查单个商品
     * @param goodsId
     * @return
     */
    @Override
    public Goods selectGoodsByGoodsId(Integer goodsId) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        return goods;
    }


}
