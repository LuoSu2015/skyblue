package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.wx.order.Data;
import com.cskaoyan.bean.wx.order.GoodForOrderList;
import com.cskaoyan.bean.wx.order.HandleOption;
import com.cskaoyan.bean.wx.order.OrderInfo;
import com.cskaoyan.mapper.AddressMapper;
import com.cskaoyan.mapper.GoodsMapper;
import com.cskaoyan.mapper.OrderGoodsMapper;
import com.cskaoyan.mapper.OrderMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderGoodsMapper orderGoodsMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    AddressMapper addressMapper;

    @Override
    public Map queryOrder(Integer showType, Integer page, Integer size, Integer userId) {
        Page<Object> pages = PageHelper.startPage(page, size);

        //showType为0, 查询所有 状态的订单
//        if(showType == 0){
            OrderExample example = new OrderExample();
            OrderExample.Criteria criteria = example.createCriteria();
            if(showType == 0){
                ArrayList<Short>  statusList = new ArrayList<>();
                statusList.add((short) 101);
                statusList.add((short) 102);
                statusList.add((short) 103);
                statusList.add((short) 201);
                statusList.add((short) 202);
                statusList.add((short) 203);
                statusList.add((short) 301);
                statusList.add((short) 401);
                criteria.andOrderStatusIn(statusList);
            }else if(showType == 1){
                criteria.andOrderStatusEqualTo((short) 101);
            }else if(showType == 2){
                criteria.andOrderStatusEqualTo((short) 201);
            }else if(showType == 3){
                criteria.andOrderStatusEqualTo((short) 301);
            }else if(showType == 4){
                criteria.andOrderStatusEqualTo((short) 401);
            }
            criteria.andDeletedEqualTo(false);
            criteria.andUserIdEqualTo(userId);
            List<Order> orders = orderMapper.selectByExample(example);
            List<Data> ordersForFront = new ArrayList<>();
//            Data data = new Data();
            for (Order order : orders) {
                //根据 订单商品关系表 中的 订单id 查询 商品id
                Data orderForFront = new Data();
                Integer id = order.getId();
                OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
                OrderGoodsExample.Criteria criteria1 = orderGoodsExample.createCriteria();
                criteria1.andOrderIdEqualTo(id);
                criteria1.andDeletedEqualTo(false);
                List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
                //根据 goodID 查询 good信息
                List<GoodForOrderList> goodsList = new ArrayList<>();
                for (OrderGoods orderGood : orderGoods) {
                    Goods goods = goodsMapper.selectByPrimaryKey(orderGood.getGoodsId());
                    GoodForOrderList goodForOrderList = new GoodForOrderList();
                    goodForOrderList.setGoodsName(goods.getName());
                    goodForOrderList.setId(goods.getId());
                    goodForOrderList.setNumber(Integer.valueOf(orderGood.getNumber()));
                    goodForOrderList.setPicUrl(goods.getPicUrl());
                    goodsList.add(goodForOrderList);
                }
                orderForFront.setGoodsList(goodsList);
                orderForFront.setActualPrice(order.getActualPrice().doubleValue());
                //isGroupin 先硬写进去
                orderForFront.setGroupin(false);
                orderForFront.setId(order.getId());
                orderForFront.setOrderSn(order.getOrderSn());
                if(order.getOrderStatus() == 101){
                    orderForFront.setOrderStatusText("未付款");
                }else if(order.getOrderStatus() == 201){
                    orderForFront.setOrderStatusText("已付款");
                }else if(order.getOrderStatus() == 102){
                    orderForFront.setOrderStatusText("用户取消");
                }else if(order.getOrderStatus() == 103){
                    orderForFront.setOrderStatusText("系统取消");
                }else if(order.getOrderStatus() == 202){
                    orderForFront.setOrderStatusText("申请退款");
                }else if(order.getOrderStatus() == 203){
                    orderForFront.setOrderStatusText("已退款");
                }else if(order.getOrderStatus() == 301){
                    orderForFront.setOrderStatusText("已发货");
                }else if(order.getOrderStatus() == 401){
                    orderForFront.setOrderStatusText("已收货");
                }
                HandleOption handleOption = new HandleOption();
                orderForFront.setHandleOption(handleOption);
                ordersForFront.add(orderForFront);
            }
            Map map = new HashMap();
            map.put("data",ordersForFront);
            map.put("count",orders.size());
            map.put("totalPages",pages.getPages());

        return map;
    }

    @Override
    public Map queryOrderDetail(Integer orderId) {
        Map map = new HashMap();
        OrderInfo orderInfo = new OrderInfo();
        Order order = orderMapper.selectByPrimaryKey(orderId);
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();
        criteria.andUserIdEqualTo(order.getUserId());
        List<Address> addresses = addressMapper.selectByExample(addressExample);
        Address address = addresses.size()>=1 ? addresses.get(0) : null;
        if(address == null){
            return new HashMap();
        }
        orderInfo.setConsignee(address.getName());
        orderInfo.setAddress(address.getAddress());
        orderInfo.setAddTime(order.getAddTime());
        orderInfo.setOrderSn(order.getOrderSn());
        orderInfo.setActualPrice(order.getActualPrice().doubleValue());
        orderInfo.setMobile(address.getMobile());
        if(order.getOrderStatus() == 101){
            orderInfo.setOrderStatusText("未付款");
        }else if(order.getOrderStatus() == 201){
            orderInfo.setOrderStatusText("已付款");
        }else if(order.getOrderStatus() == 301){
            orderInfo.setOrderStatusText("已发货");
        }else if(order.getOrderStatus() == 401){
            orderInfo.setOrderStatusText("已收货");
        }
        orderInfo.setGoodsPrice(order.getGoodsPrice().doubleValue());
        orderInfo.setCouponPrice(order.getCouponPrice().doubleValue());
        orderInfo.setId(orderId);
        orderInfo.setFreightPrice(order.getFreightPrice().doubleValue());
        HandleOption handleOption = new HandleOption();
        handleOption.setDelete(true);
        orderInfo.setHandleOption(handleOption);
        map.put("orderInfo",orderInfo);

        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        OrderGoodsExample.Criteria criteria1 = orderGoodsExample.createCriteria();
        criteria1.andOrderIdEqualTo(orderId);
        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
        map.put("orderGoods",orderGoods);
        return map;
    }

}
