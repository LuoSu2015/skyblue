package com.cskaoyan.controller;

import com.cskaoyan.bean.System;
import com.cskaoyan.bean.configManage.SystemForTransfer;
import com.cskaoyan.mapper.SystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * 配置管理模块
 * 分为: 商场配置,运费配置, 订单配置,小程序配置
 * 数据库表: cskaoyan_mall_system
 * 数据库操作: 查询,修改
 */
@RestController
@RequestMapping("admin/config")
public class ConfigManageController {

    @Autowired
    SystemMapper systemMapper;


    @RequestMapping(value = "mall", method = {RequestMethod.GET,RequestMethod.OPTIONS})
    public Map mallConfig(){
        System system1 = systemMapper.selectByPrimaryKey(6); //name
        System system2 = systemMapper.selectByPrimaryKey(8); //qq
        System system3 = systemMapper.selectByPrimaryKey(12); //phone
        System system4 = systemMapper.selectByPrimaryKey(14); //address
        Map map = new HashMap<>();
        Map map1 = new HashMap();
        map1.put(system3.getKeyName(),system3.getKeyValue());
        map1.put(system4.getKeyName(),system4.getKeyValue());
        map1.put(system1.getKeyName(),system1.getKeyValue());
        map1.put(system2.getKeyName(),system2.getKeyValue());
        /*SystemForTransfer systemForTransfer = new SystemForTransfer();
        systemForTransfer.setLitemall_mall_name(system1.getKeyValue());
        systemForTransfer.setLitemall_mall_qq(system2.getKeyValue());
        systemForTransfer.setLitemall_mall_phone(system3.getKeyValue());
        systemForTransfer.setLitemall_mall_address(system4.getKeyValue());*/
        map.put("errno",0);
        map.put("data",map1);
        map.put("errmsg","成功");
        return map;
    }
    @RequestMapping(value = "mall", method = RequestMethod.POST)
    public Map mallConfig(@RequestBody SystemForTransfer systemForTransfer){
        String address = systemForTransfer.getCskaoyan_mall_mall_address();
        String name = systemForTransfer.getCskaoyan_mall_mall_name();
        String phone = systemForTransfer.getCskaoyan_mall_mall_phone();
        String qq = systemForTransfer.getCskaoyan_mall_mall_qq();
        System system = new System();
        system.setKeyValue(address);
        system.setId(14);
        systemMapper.updateByPrimaryKeySelective(system);
        system.setKeyValue(name);
        system.setId(6);
        systemMapper.updateByPrimaryKeySelective(system);
        system.setKeyValue(qq);
        system.setId(8);
        systemMapper.updateByPrimaryKeySelective(system);
        system.setKeyValue(phone);
        system.setId(12);
        systemMapper.updateByPrimaryKeySelective(system);
        Map map = new HashMap();
        map.put("errno",0);
        map.put("errmsg","成功");
        return map;
    }
    @RequestMapping(value = "express",method = {RequestMethod.GET,RequestMethod.OPTIONS})
    public Map expressConfig(){
        System system1 = systemMapper.selectByPrimaryKey(5); //min
        System system2 = systemMapper.selectByPrimaryKey(7); //freight_value
        Map map = new HashMap<>();
        Map map1 = new HashMap();
        map1.put(system1.getKeyName(),system1.getKeyValue());
        map1.put(system2.getKeyName(),system2.getKeyValue());
        map.put("errno",0);
        map.put("data",map1);
        map.put("errmsg","成功");
        return map;
    }
    @RequestMapping(value = "express",method = {RequestMethod.POST,RequestMethod.OPTIONS})
    public Map expressConfig(@RequestBody SystemForTransfer systemForTransfer){
        String min = systemForTransfer.getCskaoyan_mall_express_freight_min();
        String value = systemForTransfer.getCskaoyan_mall_express_freight_value();
        System system = new System();
        system.setKeyValue(min);
        system.setId(5);
        systemMapper.updateByPrimaryKeySelective(system);
        system.setKeyValue(value);
        system.setId(7);
        systemMapper.updateByPrimaryKeySelective(system);
        Map map = new HashMap();
        map.put("errno",0);
        map.put("errmsg","成功");
        return map;
    }
    @RequestMapping(value = "order",method = {RequestMethod.GET})
    public Map orderConfig(){
        System system1 = systemMapper.selectByPrimaryKey(1); //unpaid
        System system2 = systemMapper.selectByPrimaryKey(3); //unconfirm
        System system3 = systemMapper.selectByPrimaryKey(10); //comment
        Map map = new HashMap<>();
        Map map1 = new HashMap();
        map1.put(system1.getKeyName(),system1.getKeyValue());
        map1.put(system2.getKeyName(),system2.getKeyValue());
        map1.put(system3.getKeyName(),system3.getKeyValue());
        map.put("errno",0);
        map.put("data",map1);
        map.put("errmsg","成功");
        return map;
    }
    @RequestMapping(value = "order",method = {RequestMethod.POST})
    public Map orderConfig(@RequestBody SystemForTransfer systemForTransfer){
        String order_unpaid = systemForTransfer.getCskaoyan_mall_order_unpaid();
        String order_unconfirm = systemForTransfer.getCskaoyan_mall_order_unconfirm();
        String order_comment = systemForTransfer.getCskaoyan_mall_order_comment();
        System system = new System();
        system.setKeyValue(order_unpaid);
        system.setId(1);
        systemMapper.updateByPrimaryKeySelective(system);
        system.setKeyValue(order_unconfirm);
        system.setId(3);
        systemMapper.updateByPrimaryKeySelective(system);
        system.setKeyValue(order_comment);
        system.setId(10);
        systemMapper.updateByPrimaryKeySelective(system);
        Map map = new HashMap();
        map.put("errno",0);
        map.put("errmsg","成功");
        return map;
    }
    @RequestMapping(value = "wx",method = {RequestMethod.GET})
    public Map wxConfig(){
        System system1 = systemMapper.selectByPrimaryKey(2); //new
        System system2 = systemMapper.selectByPrimaryKey(4); //share
        System system3 = systemMapper.selectByPrimaryKey(9); //index_hot
        System system4 = systemMapper.selectByPrimaryKey(11); //goods
        System system5 = systemMapper.selectByPrimaryKey(13); //list
        System system6 = systemMapper.selectByPrimaryKey(15); //brand
        System system7 = systemMapper.selectByPrimaryKey(16); //topic
        Map map = new HashMap<>();
        Map map1 = new HashMap();
        map1.put(system1.getKeyName(),system1.getKeyValue());
        map1.put(system2.getKeyName(),system2.getKeyValue());
        map1.put(system3.getKeyName(),system3.getKeyValue());
        map1.put(system4.getKeyName(),system4.getKeyValue());
        map1.put(system5.getKeyName(),system5.getKeyValue());
        map1.put(system6.getKeyName(),system6.getKeyValue());
        map1.put(system7.getKeyName(),system7.getKeyValue());
        map.put("errno",0);
        map.put("data",map1);
        map.put("errmsg","成功");
        return map;
    }
    @RequestMapping(value = "wx",method = {RequestMethod.POST})
    public Map wxConfig(@RequestBody SystemForTransfer systemForTransfer){
        String index_new = systemForTransfer.getCskaoyan_mall_wx_index_new();//2
        String wx_share = systemForTransfer.getCskaoyan_mall_wx_share(); //4
        String index_hot = systemForTransfer.getCskaoyan_mall_wx_index_hot(); // 9
        String index_topic = systemForTransfer.getCskaoyan_mall_wx_index_topic(); //16
        String index_brand = systemForTransfer.getCskaoyan_mall_wx_index_brand(); //15
        String catlog_list = systemForTransfer.getCskaoyan_mall_wx_catlog_list(); //13
        String catlog_goods = systemForTransfer.getCskaoyan_mall_wx_catlog_goods(); //11

        System system = new System();
        system.setKeyValue(index_new);
        system.setId(2);
        systemMapper.updateByPrimaryKeySelective(system);
        system.setKeyValue(wx_share);
        system.setId(4);
        systemMapper.updateByPrimaryKeySelective(system);
        system.setKeyValue(index_hot);
        system.setId(9);
        systemMapper.updateByPrimaryKeySelective(system);
        system.setKeyValue(index_topic);
        system.setId(16);
        systemMapper.updateByPrimaryKeySelective(system);
        system.setKeyValue(index_brand);
        system.setId(15);
        systemMapper.updateByPrimaryKeySelective(system);
        system.setKeyValue(catlog_list);
        system.setId(13);
        systemMapper.updateByPrimaryKeySelective(system);
        system.setKeyValue(catlog_goods);
        system.setId(11);
        systemMapper.updateByPrimaryKeySelective(system);
        Map map = new HashMap<>();
        map.put("errno",0);
        map.put("errmsg","成功");
        return map;
    }
}
