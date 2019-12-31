package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WxOrderController {

    @Autowired
    OrderMapper orderMapper;

    @RequestMapping("wx/auth/login")
    public BaseRespVo login(){
        Map map = new HashMap();
        BaseRespVo baseRespVo = new BaseRespVo();
//        map.put("errno",0);
        baseRespVo.setErrno(0);
        Map map1 = new HashMap();
        Map map2 = new HashMap();
        map2.put("nickname","lanzhao");
        map2.put("avatarUrl","");
        map1.put("userInfo",map2);
        map1.put("tokenExpire","2019-12-31T21:56:37.128");
        map1.put("token","123");
//        map.put("data",map1);
//        map.put("errmsg","成功");
        baseRespVo.setData(map1);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }


    /**
     * 501 请登录
     */
    @RequestMapping("wx/order/list")
    public BaseRespVo getOrderList(Integer showType, Integer page, Integer size){
        orderMapper.selectStatisOrders();
        BaseRespVo baseRespVo = new BaseRespVo();
        return baseRespVo;
    }
}
