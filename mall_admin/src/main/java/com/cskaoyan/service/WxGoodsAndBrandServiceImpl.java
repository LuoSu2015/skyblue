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
    @Autowired
    CollectMapper collectMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    GrouponRulesMapper grouponRulesMapper;


    @Override
    public Brand slectBrandById(int id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        return brand;
    }

    @Override
    public List<Goods> selectGoodsWithMulty(Integer brandId,Integer page,Integer size,String order,String sort,Boolean isHot,Integer categoryId,Boolean isNew,String keyword) {
        GoodsExample goodsExample = new GoodsExample();
        if(keyword != null){

        }
        //排序
        if(sort!=null && order!=null){
            goodsExample.setOrderByClause(sort + " " + order);
        }
        GoodsExample.Criteria criteria1 = goodsExample.createCriteria();
        if(keyword != null){
            criteria1.andKeywordsLike("%" + keyword + "%");
        }
        if(brandId != null){
            criteria1.andBrandIdEqualTo(brandId);
        }
        if(isHot != null){
            criteria1.andIsHotEqualTo(isHot);
        }
        if(isNew != null){
            criteria1.andIsNewEqualTo(isNew);
        }
        if(categoryId != null && categoryId != 0){
            criteria1.andCategoryIdEqualTo(categoryId);
        }
        criteria1.andDeletedEqualTo(false);

        //分页
        PageHelper.startPage(page,size);
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        return goodsList;

    }

    @Override
    public Long countGoods() {
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        long goodsCount = goodsMapper.countByExample(goodsExample);
        return goodsCount;
    }

    @Override
    public Category selectCategoryById(Integer id) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andDeletedEqualTo(false);
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
        criteria.andDeletedEqualTo(false);
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
        criteria.andDeletedEqualTo(false);
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
        criteria.andDeletedEqualTo(false);
        List<GoodsProduct> goodsProducts = goodsProductMapper.selectByExample(goodsProductExample);

        return goodsProducts;
    }

    @Override
    public List<GoodsAttribute> selectGoodsAttributeByGoodsId(Integer goodsId) {
        GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
        GoodsAttributeExample.Criteria criteria = goodsAttributeExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        criteria.andDeletedEqualTo(false);
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

    /**
     * 统计收藏商品的用户数量
     * @param goodsId
     * @return
     */
    @Override
    public Long countCollect(Integer goodsId) {
        CollectExample collectExample = new CollectExample();
        CollectExample.Criteria criteria = collectExample.createCriteria();
        criteria.andValueIdEqualTo(goodsId);
        byte b = 0;
        criteria.andTypeEqualTo(b);
        criteria.andDeletedEqualTo(false);
        long count = collectMapper.countByExample(collectExample);
        return count;
    }


    @Override
    public Long countComment(Integer goodsId) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andValueIdEqualTo(goodsId);
        byte b = 0;
        criteria.andTypeEqualTo(b);
        long count = commentMapper.countByExample(commentExample);
        return count;
    }

    @Override
    public List<Comment> selectCommetByGoodsId(Integer goodsId) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andValueIdEqualTo(goodsId);
        byte b = 0;
        criteria.andTypeEqualTo(b);
        criteria.andDeletedEqualTo(false);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        return comments ;
    }

    @Override
    public List<GrouponRules> selectGroupOnByGoodsId(Integer goodsId) {
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        GrouponRulesExample.Criteria criteria = grouponRulesExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        criteria.andDeletedEqualTo(false);
        List<GrouponRules> grouponRules = grouponRulesMapper.selectByExample(grouponRulesExample);
        return grouponRules;
    }

    @Override
    public List<Goods> selectGoodsByCategoryId(Integer categoryId) {
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        criteria.andDeletedEqualTo(false);
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        return goodsList;
    }


}
