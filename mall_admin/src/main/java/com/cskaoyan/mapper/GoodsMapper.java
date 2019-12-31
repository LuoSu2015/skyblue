package com.cskaoyan.mapper;

import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.GoodsExample;
import java.util.List;

import com.cskaoyan.bean.WxGrouponList;
import org.apache.ibatis.annotations.Param;

public interface GoodsMapper {
    long countByExample(GoodsExample example);

    int deleteByExample(GoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    List<Goods> selectByExampleWithBLOBs(GoodsExample example);

    List<Goods> selectByExample(GoodsExample example);

    Goods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExampleWithBLOBs(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExample(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    List<Goods> selectAllGoods(@Param("sort") String sort,@Param("order") String order,@Param("goodsSn") Integer goodsSn,@Param("name") String name);

    List<WxGrouponList> selectGoodsByGroupon();
}
