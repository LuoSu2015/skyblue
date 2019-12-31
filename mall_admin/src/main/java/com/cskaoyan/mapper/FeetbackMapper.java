package com.cskaoyan.mapper;

import com.cskaoyan.bean.Feetback;
import com.cskaoyan.bean.FeetbackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FeetbackMapper {
    long countByExample(FeetbackExample example);

    int deleteByExample(FeetbackExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Feetback record);

    int insertSelective(Feetback record);

    List<Feetback> selectByExample(FeetbackExample example);

    Feetback selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Feetback record, @Param("example") FeetbackExample example);

    int updateByExample(@Param("record") Feetback record, @Param("example") FeetbackExample example);

    int updateByPrimaryKeySelective(Feetback record);

    int updateByPrimaryKey(Feetback record);
}