package com.cskaoyan.service.statistics;

import com.cskaoyan.bean.statistics.StatisGoods;
import com.cskaoyan.bean.statistics.StatisOrders;
import com.cskaoyan.bean.statistics.StatisUser;
import com.cskaoyan.mapper.OrderMapper;
import com.cskaoyan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaticsticsServiceImpl implements StatisticsService {
    @Autowired
    UserMapper userMapper;


    /**
     * 统计用户
     * @return
     */
    @Override
    public List<StatisUser> queryStatisUser() {
        return userMapper.selectStatisUser();
    }


    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<StatisGoods> queryStatisGoods() {
        return orderMapper.selectStatisGoods();
    }

    @Override
    public List<StatisOrders> queryStatisOrders() {
        return orderMapper.selectStatisOrders();
    }

}
