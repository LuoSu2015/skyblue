package com.cskaoyan.mapper;

import com.cskaoyan.bean.Category;
import com.cskaoyan.bean.CategoryExample;
import java.util.List;

import com.cskaoyan.bean.goods.MyCategory;
import org.apache.ibatis.annotations.Param;

public interface CategoryMapper {
    long countByExample(CategoryExample example);

    int deleteByExample(CategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<MyCategory> selectAllCategory();

    List<Category> getCategoryList();

    List<Category> getSubCategoryByPid(@Param("parentId") Integer parentId);
}
