package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.statistics.StatisGoods;
import com.cskaoyan.bean.statistics.StatisOrders;
import com.cskaoyan.bean.statistics.StatisUser;
import com.cskaoyan.mapper.UserMapper;


import com.cskaoyan.service.statistics.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StatisticsController {

    @Autowired
    StatisUser statisUser;

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    UserMapper userMapper;

    /**
     * 统计报表,统计用户
     * @return
     */
    @RequestMapping("admin/stat/user")
    public BaseRespVo countUser(){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        Map<String,Object> map = new HashMap<>();
        ArrayList<Object> list1 = new ArrayList<>();
        list1.add("day");
        list1.add("users");
        map.put("columns",list1);
        LocalDate today = LocalDate.now();
        List<StatisUser> statisUsers = statisticsService.queryStatisUser();
        map.put("rows",statisUsers);
        baseRespVo.setData(map);
        return baseRespVo;
    }

    @RequestMapping("admin/stat/goods")
    public BaseRespVo countGoods(){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        ArrayList list1 = new ArrayList();
        HashMap map = new HashMap();
        list1.add("day");
        list1.add("orders");
        list1.add("products");
        list1.add("amount");
        map.put("columns",list1);
        List<StatisGoods> statisGoods = statisticsService.queryStatisGoods();
        map.put("rows",statisGoods);
        baseRespVo.setData(map);
        return baseRespVo;
    }


    @RequestMapping("admin/stat/order")
    public BaseRespVo countOrders(){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        ArrayList list1 = new ArrayList();
        HashMap map = new HashMap();
        list1.add("day");
        list1.add("orders");
        list1.add("customers");
        list1.add("amount");
        list1.add("pcr");
        map.put("columns",list1);
        List<StatisOrders> statisOrders = statisticsService.queryStatisOrders();
        map.put("rows",statisOrders);
        baseRespVo.setData(map);
        return baseRespVo;
    }
}
