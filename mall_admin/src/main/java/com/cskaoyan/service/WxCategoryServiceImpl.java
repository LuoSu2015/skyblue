package com.cskaoyan.service;

import com.cskaoyan.bean.Category;
import com.cskaoyan.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxCategoryServiceImpl implements WxCategoryService{
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> getCategoryList() {
        List<Category> categoryList = categoryMapper.getCategoryList();
        return categoryList;
    }

    @Override
    public List<Category> getSubCategoryByPid(Integer parentId) {
        List<Category> currentSubCategory = categoryMapper.getSubCategoryByPid(parentId);
        return currentSubCategory;
    }
}
