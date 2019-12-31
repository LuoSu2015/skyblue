package com.cskaoyan.service;

import com.cskaoyan.bean.Category;

import java.util.List;

public interface WxCategoryService {

    List<Category> getCategoryList();

    List<Category> getSubCategoryByPid(Integer parentId);

}
