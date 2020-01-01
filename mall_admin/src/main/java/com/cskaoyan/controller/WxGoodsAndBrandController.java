package com.cskaoyan.controller;

import com.cskaoyan.bean.*;
import com.cskaoyan.service.MarketService;
import com.cskaoyan.service.WxGoodsAndBrandService;
import com.cskaoyan.service.WxGoodsAndBrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WxGoodsAndBrandController {


    @Autowired
    MarketService marketService;

    @Autowired
    WxGoodsAndBrandService wxGoodsAndBrandService;

    /**
     * 品牌列表
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("wx/brand/list")
    public BaseRespVo showBrandList(Integer page,Integer size){
        BaseRespVo baseRespVo = new BaseRespVo();
        String sort = "add_time";
        String order = "desc";
        Integer id = null;
        String name = null;
        Map map = marketService.queryBrand(page, size, sort, order,id,name);
        List<Brand> brands = (List<Brand>) map.get("brands");
        Integer total = (Integer) map.get("total");
        total = total/size + 1;
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        Map resultMap = new HashMap();
        resultMap.put("totalPages",total);
        resultMap.put("brandList",brands);
        baseRespVo.setData(resultMap);
        return baseRespVo;
    }

    /**
     *品牌详情
     * @param id
     * @return
     */
    @RequestMapping("wx/brand/detail")
    public BaseRespVo showBrandDetail(int id){
        BaseRespVo baseRespVo = new BaseRespVo();
        Brand brand = wxGoodsAndBrandService.slectBrandById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("brand",brand);
        baseRespVo.setData(map);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 获得商品列表
     * @param brandId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("wx/goods/list")
    public BaseRespVo showGoodsDetail(Integer brandId,Integer page,Integer size,String order,String sort,Boolean isHot,Integer categoryId,Boolean isNew,String keyword){
        BaseRespVo baseRespVo = new BaseRespVo();
        List<Goods> goodsList = wxGoodsAndBrandService.selectGoodsWithMulty(brandId, page, size,order,sort,isHot,categoryId,isNew,keyword);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        HashMap<String, Object> map = new HashMap<>();
        map.put("goodsList",goodsList);
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     *统计商品总数
     */
   /* @RequestMapping("wx/goods/count")
    public BaseRespVo countGoods(){
        BaseRespVo baseRespVo = new BaseRespVo();
        Long goodsCount = wxGoodsAndBrandService.countGoods();
        HashMap<String,Object> map = new HashMap();
        map.put("goodsCount",goodsCount);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }*/

    /**
     * 获得分类数据
     * @return
     */
    @RequestMapping("wx/goods/category")
    public BaseRespVo getCategoryFromGoods(Integer id){
        BaseRespVo baseRespVo = new BaseRespVo();
        Category currentCategory = wxGoodsAndBrandService.selectCategoryById(id);
        Integer categoryId = currentCategory.getPid();
        List<Category> brotherCategory = wxGoodsAndBrandService.selectCategoryByPid(categoryId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("currentCategory",currentCategory);
        map.put("brotherCategory",brotherCategory);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 获得商品详情
     * @param
     * @return
     */
    @RequestMapping("wx/goods/detail")
    public BaseRespVo getGoodsDetail(Integer id){
        BaseRespVo baseRespVo = new BaseRespVo();
        List<GoodsSpecification> goodsSpecifications = wxGoodsAndBrandService.selectSpecificationByGoodsId(id);
        ArrayList specificationList = new ArrayList();
        HashMap<String,Object> map = new HashMap();
        for (GoodsSpecification goodsSpecification : goodsSpecifications) {
            String specification = goodsSpecification.getSpecification();
            List<GoodsSpecification> goodsSpecifications1 = wxGoodsAndBrandService.selectSpecificationByGoodsIsAndSpecification(id, specification);
            HashMap<String,Object> currentMap = new HashMap();
            currentMap.put("valueList",goodsSpecifications1);
            currentMap.put("name",specification);
            specificationList.add(currentMap);
        }
        List<Issue> issue = wxGoodsAndBrandService.selectIssue();
        List<GoodsProduct> productList = wxGoodsAndBrandService.selectGoodsProductByGoodsId(id);
        List<GoodsAttribute> attribute = wxGoodsAndBrandService.selectGoodsAttributeByGoodsId(id);
        Goods info = wxGoodsAndBrandService.selectGoodsByGoodsId(id);
        Integer brandId = info.getBrandId();
        Brand brand = wxGoodsAndBrandService.slectBrandById(brandId);
        Long userHasCollect = wxGoodsAndBrandService.countCollect(id);
        HashMap<String,Object> commentMap = new HashMap();
        Long count = wxGoodsAndBrandService.countComment(id);
        List<Comment> data = wxGoodsAndBrandService.selectCommetByGoodsId(id);
        commentMap.put("data",data);
        commentMap.put("count",count);
        List<GrouponRules> groupon = wxGoodsAndBrandService.selectGroupOnByGoodsId(id);
        map.put("specificationList",specificationList);
        map.put("issue",issue);
        map.put("userHasCollect",userHasCollect);
        map.put("shareImage","");
        map.put("comment",commentMap);
        map.put("attribute",attribute);
        map.put("brand",brand);
        map.put("productList",productList);
        map.put("info",info);
        map.put("groupon",groupon);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }


    @RequestMapping("wx/goods/related")
    public BaseRespVo getRelatedGoods(Integer id){
        BaseRespVo baseRespVo = new BaseRespVo();
        Goods goods = wxGoodsAndBrandService.selectGoodsByGoodsId(id);
        Integer categoryId = goods.getCategoryId();
        List<Goods> goodsList = wxGoodsAndBrandService.selectGoodsByCategoryId(categoryId);
        HashMap<String,Object> map = new HashMap();
        map.put("goodsList",goodsList);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

}
