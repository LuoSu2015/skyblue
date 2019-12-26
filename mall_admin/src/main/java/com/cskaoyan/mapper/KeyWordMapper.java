package com.cskaoyan.mapper;

import com.cskaoyan.bean.KeyWord;
import com.cskaoyan.bean.KeyWordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface KeyWordMapper {
    long countByExample(KeyWordExample example);

    int deleteByExample(KeyWordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(KeyWord record);

    int insertSelective(KeyWord record);

    List<KeyWord> selectByExample(KeyWordExample example);

    KeyWord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") KeyWord record, @Param("example") KeyWordExample example);

    int updateByExample(@Param("record") KeyWord record, @Param("example") KeyWordExample example);

    int updateByPrimaryKeySelective(KeyWord record);

    int updateByPrimaryKey(KeyWord record);
}