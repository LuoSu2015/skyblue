package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Category;
import com.cskaoyan.mapper.CategoryMapper;
import com.cskaoyan.service.WxCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WxCategoryController {
    @Autowired
    WxCategoryService wxCategoryService;

    @RequestMapping("wx/catalog/index")
    public BaseRespVo showCategory(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();

        List<Category> categoryList = wxCategoryService.getCategoryList();
        Category currentCategory = categoryList.get(0);
        List<Category> currentSubCategory = wxCategoryService.getSubCategoryByPid(currentCategory.getId());

        data.put("categoryList",categoryList);
        data.put("currentCategory",currentCategory);
        data.put("currentSubCategory",currentSubCategory);

        baseRespVo.setErrno(0);
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("wx/catalog/current")
    public BaseRespVo showCategory(@RequestParam Integer id){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();

        Category currentCategory = null;
        List<Category> categoryList = wxCategoryService.getCategoryList();
        for (Category category : categoryList) {
            int categoryId = category.getId();
            if (categoryId == (int)id){
                currentCategory = category;
                break;
            }
        }
        List<Category> currentSubCategory = wxCategoryService.getSubCategoryByPid(id);
        data.put("currentCategory",currentCategory);
        data.put("currentSubCategory",currentSubCategory);

        baseRespVo.setErrno(0);
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
