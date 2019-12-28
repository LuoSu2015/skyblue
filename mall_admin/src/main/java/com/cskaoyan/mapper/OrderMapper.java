package com.cskaoyan.mapper;

import com.cskaoyan.bean.Order;
import com.cskaoyan.bean.OrderExample;
import java.util.List;

import com.cskaoyan.bean.statistics.StatisGoods;
import com.cskaoyan.bean.statistics.StatisOrders;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    long countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<StatisGoods> selectStatisGoods();

    List<StatisOrders> selectStatisOrders();
}
