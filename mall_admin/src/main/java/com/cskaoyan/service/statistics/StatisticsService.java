package com.cskaoyan.service.statistics;

import com.cskaoyan.bean.statistics.StatisGoods;
import com.cskaoyan.bean.statistics.StatisOrders;
import com.cskaoyan.bean.statistics.StatisUser;

import java.util.List;

public interface StatisticsService {
    /**
     * 统计用户
     * @return
     */
    List<StatisUser> queryStatisUser();

    List<StatisGoods> queryStatisGoods();

    List<StatisOrders> queryStatisOrders();

}
