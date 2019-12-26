package com.skyblue.mapper;

import com.cskaoyan.bean.Adress;
import com.cskaoyan.bean.AdressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdressMapper {
    long countByExample(AdressExample example);

    int deleteByExample(AdressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Adress record);

    int insertSelective(Adress record);

    List<Adress> selectByExample(AdressExample example);

    Adress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Adress record, @Param("example") AdressExample example);

    int updateByExample(@Param("record") Adress record, @Param("example") AdressExample example);

    int updateByPrimaryKeySelective(Adress record);

    int updateByPrimaryKey(Adress record);
}