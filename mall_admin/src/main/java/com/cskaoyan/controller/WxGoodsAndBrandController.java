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
    public BaseRespVo showGoodsDetail(Integer brandId,Integer page,Integer size,String order,String sort,Boolean isHot,Integer categoryId,Boolean isNew){
        BaseRespVo baseRespVo = new BaseRespVo();
        List<Goods> goodsList = wxGoodsAndBrandService.selectGoodsWithMulty(brandId, page, size,order,sort,isHot,categoryId,isNew);
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

    /*@RequestMapping("wx/goods/detail")
    public BaseRespVo getGoodsDetail(Integer goodsId){
        BaseRespVo baseRespVo = new BaseRespVo();
        List<GoodsSpecification> goodsSpecifications = wxGoodsAndBrandService.selectSpecificationByGoodsId(goodsId);
        ArrayList specificationList = new ArrayList();
        HashMap<String,Object> specificationMap = new HashMap();
        for (GoodsSpecification goodsSpecification : goodsSpecifications) {
            String specification = goodsSpecification.getSpecification();
            List<GoodsSpecification> goodsSpecifications1 = wxGoodsAndBrandService.selectSpecificationByGoodsIsAndSpecification(goodsId, specification);
            specificationMap.put(specification,goodsSpecifications1);
        }

        List<Issue> issue = wxGoodsAndBrandService.selectIssue();

        List<GoodsProduct> goodsProducts = wxGoodsAndBrandService.selectGoodsProductByGoodsId(goodsId);

        List<GoodsAttribute> goodsAttributes = wxGoodsAndBrandService.selectGoodsAttributeByGoodsId(goodsId);

        Goods goods = wxGoodsAndBrandService.selectGoodsByGoodsId(goodsId);
        Integer brandId = goods.getBrandId();
        Brand brand = wxGoodsAndBrandService.slectBrandById(brandId);

    }*/

}
