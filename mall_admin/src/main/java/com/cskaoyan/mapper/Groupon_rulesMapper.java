package com.cskaoyan.mapper;

import com.cskaoyan.bean.Groupon_rules;
import com.cskaoyan.bean.Groupon_rulesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Groupon_rulesMapper {
    long countByExample(Groupon_rulesExample example);

    int deleteByExample(Groupon_rulesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Groupon_rules record);

    int insertSelective(Groupon_rules record);

    List<Groupon_rules> selectByExample(Groupon_rulesExample example);

    Groupon_rules selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Groupon_rules record, @Param("example") Groupon_rulesExample example);

    int updateByExample(@Param("record") Groupon_rules record, @Param("example") Groupon_rulesExample example);

    int updateByPrimaryKeySelective(Groupon_rules record);

    int updateByPrimaryKey(Groupon_rules record);
}