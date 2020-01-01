package com.cskaoyan.mapper;

import com.cskaoyan.bean.Collect;
import com.cskaoyan.bean.CollectExample;
import java.util.List;

import com.cskaoyan.bean.WxMyCollect;
import org.apache.ibatis.annotations.Param;

public interface CollectMapper {
    long countByExample(CollectExample example);

    int deleteByExample(CollectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Collect record);

    int insertSelective(Collect record);

    List<Collect> selectByExample(CollectExample example);

    Collect selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByExample(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    List<WxMyCollect> selectGoodsCollectByUserId(@Param("userId") Integer userId);

    Collect getCollect(@Param("type") Integer type,@Param("valueId") Integer valueId,@Param("userId") Integer userId);

    void reverseDeleted(@Param("type") Integer type,@Param("valueId") Integer valueId,@Param("userId") Integer userId,@Param("deleted") boolean deleted);

    void insertCollect(@Param("type") Integer type, @Param("valueId") Integer valueId, @Param("userId") Integer userId);
}
