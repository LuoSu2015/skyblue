package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.wx.OrderStatus;
import com.cskaoyan.bean.wx.order.*;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

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
    @Autowired
    CartMapper cartMapper;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    GrouponRulesMapper grouponRulesMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;

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
                HandleOption handleOption = new HandleOption();
                if(order.getOrderStatus() == 101){
                    orderForFront.setOrderStatusText("未付款");
                }else if(order.getOrderStatus() == 201){
                    orderForFront.setOrderStatusText("已付款");
                    handleOption.setPay(false);
                }else if(order.getOrderStatus() == 102){
                    orderForFront.setOrderStatusText("用户取消");
                }else if(order.getOrderStatus() == 103){
                    orderForFront.setOrderStatusText("系统取消");
                }else if(order.getOrderStatus() == 202){
                    orderForFront.setOrderStatusText("申请退款");
                    handleOption.setRefund(true);
                }else if(order.getOrderStatus() == 203){
                    orderForFront.setOrderStatusText("已退款");
                }else if(order.getOrderStatus() == 301){
                    orderForFront.setOrderStatusText("已发货");
                }else if(order.getOrderStatus() == 401){
                    orderForFront.setOrderStatusText("已收货");
                    handleOption.setComment(true);
                }
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
        HandleOption handleOption = new HandleOption();
        if(order.getOrderStatus() == 101){
            orderInfo.setOrderStatusText("未付款");
            handleOption.setPay(true);
        }else if(order.getOrderStatus() == 201){
            orderInfo.setOrderStatusText("已付款");
        }else if(order.getOrderStatus() == 301){
            orderInfo.setOrderStatusText("已发货");
            handleOption.setConfirm(true);
        }else if(order.getOrderStatus() == 401){
            orderInfo.setOrderStatusText("已收货");
            handleOption.setComment(true);
        }
        orderInfo.setGoodsPrice(order.getGoodsPrice().doubleValue());
        orderInfo.setCouponPrice(order.getCouponPrice().doubleValue());
        orderInfo.setId(orderId);
        orderInfo.setFreightPrice(order.getFreightPrice().doubleValue());
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


    /**
     * @return
     */
    @Override
    @Transactional
    public Integer createOrder(User user) {
        //根据addressId查找地址信息
        Address address = addressMapper.selectByPrimaryKey(1);
        //获取购物车信息
        //Cart cart = cartMapper.selectByPrimaryKey(cartId);
        //获取user信息
       // Integer userId = cart.getUserId();
        User user1 = userMapper.selectByPrimaryKey(user.getId());
        //获取优惠券信息
        //Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        //获取商品信息
       // Goods goods = goodsMapper.selectByPrimaryKey(cart.getGoodsId());
        //订单状态
        Short orderStatus = 201;
        //当前时间
        Date nowTime = new Date();
        //数量
        Short commit = 10;
        //配送费用
        BigDecimal max = new BigDecimal("88.00");
        BigDecimal price = new BigDecimal(0);
      /*  if(max.compareTo(cart.getPrice()) > 0){
            price = price.add(new BigDecimal(8));
        }*/
        Order order = new Order();
        order.setUserId(user.getId());
        order.setOrderSn("10001");
        order.setOrderStatus(orderStatus);
        order.setConsignee(address.getName());
        order.setMobile(user.getMobile());
        order.setAddress(address.getAddress());
        order.setMessage("ok");
        order.setGoodsPrice(new BigDecimal(0));
        order.setFreightPrice(price);
        order.setCouponPrice(price);
        order.setIntegralPrice(new BigDecimal(0));
        order.setGrouponPrice(new BigDecimal(0));
        order.setOrderPrice(new BigDecimal(0));
        order.setActualPrice(new BigDecimal(0));
        order.setPayId("1");
        order.setPayTime(nowTime);
        order.setShipSn("101");
        order.setShipTime(nowTime);
        order.setConfirmTime(nowTime);
        order.setComments(commit);
        order.setEndTime(nowTime);
        order.setAddTime(nowTime);
        order.setDeleted(false);
      /*  OrderGoods orderGoods = new OrderGoods();
        orderGoods.setOrderId(order.getId());
        orderGoods.setComment(0);
        orderGoodsMapper.insert(orderGoods);*/
        int insert = orderMapper.insert(order);
        return insert;
    }


    /**
     * 删除订单逻辑,将订单的字段deleted更新为true
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public int deleteOrderById(Integer orderId) {
        //将订单id为orderId的订单删除
        Order order = orderMapper.selectByPrimaryKey(orderId);
        //更新订单表的deleted字段
        order.setDeleted(true);
        int i = orderMapper.updateByPrimaryKey(order);
        //查询订单和goods关系表的id
        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        orderGoodsExample.createCriteria().andOrderIdEqualTo(orderId);
        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
        //更新订单和goods关系表的deleted字段
        for (OrderGoods orderGood : orderGoods) {
            orderGood.setDeleted(true);
            orderGoodsMapper.updateByPrimaryKey(orderGood);
        }
        return i;
    }

    /**
     * 确认收货逻辑
     * @param id
     * @return
     */
    @Override
    public int orderConfirm(int id) {
        //查询订单信息
        Order order = orderMapper.selectByPrimaryKey(id);
        Short status = 401;
        order.setOrderStatus(status);
        //更新订单信息
        int i = orderMapper.updateByPrimaryKeySelective(order);
        return i;
    }

    @Override
    public Order queryOrderById(Integer id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        return order;
    }

    /**
     *付款逻辑
     * @param id
     * @return
     */
    @Override
    public int payOrders(Integer id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        Short orderStatus = 201;
        order.setOrderStatus(orderStatus);
        int i = orderMapper.updateByPrimaryKey(order);
        return i;
    }

    /**
     * 查询订单状态
     * @param user
     * @return
     */
    @Override
    public OrderStatus selectOrderStatus(User user) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUserIdEqualTo(user.getId());
        List<Order> orders = orderMapper.selectByExample(orderExample);

        OrderExample orderExample1 = new OrderExample();
        Short status = 101;
        orderExample1.createCriteria().andOrderStatusEqualTo(status);

        OrderStatus orderStatus = new OrderStatus();
        Integer uncomment = 0;
        Integer unpaid = 0;
        Integer unrecv = 0;
        Integer unship = 0;
        for (Order order : orders) {
            if(order.getOrderStatus() == 101){
                unpaid += 1;
            }
            if(order.getOrderStatus() == 201){
                unship += 1;
            }
            if(order.getOrderStatus() == 301){
                unrecv += 1;
            }
            if(order.getOrderStatus() == 401){
                uncomment += 1;
            }
        }
        orderStatus.setUncomment(uncomment);
        orderStatus.setUnpaid(unpaid);
        orderStatus.setUnrecv(unrecv);
        orderStatus.setUnship(unship);
        return orderStatus;
    }

    /**
     * 显示评价信息
     * @param goodsId
     * @param orderId
     * @return
     */
    @Override
    public OrderGoodsReturn selectOrderGoodsReturn(Integer goodsId, Integer orderId) {
        OrderGoodsReturn orderGoodsReturn = new OrderGoodsReturn();
        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        orderGoodsExample.createCriteria().andOrderIdEqualTo(orderId).andGoodsIdEqualTo(goodsId);
        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
        OrderGoods orderGood = orderGoods.get(0);
        orderGoodsReturn.setGoodsName(orderGood.getGoodsName());
        orderGoodsReturn.setGoodsSpecificationValues(orderGood.getSpecifications());
        orderGoodsReturn.setNumber(orderGood.getNumber());
        orderGoodsReturn.setPicUrl(orderGood.getPicUrl());
        return orderGoodsReturn;
    }

    /**
     *
     * @param comment
     * @return
     */
    @Override
    @Transactional
    public int insertComment(Comment comment,User user) {
        Date nowTime = new Date();
        comment.setValueId(0);
        Byte type = 0;
        comment.setType(type);
        comment.setUserId(user.getId());
        comment.setAddTime(nowTime);
        comment.setUpdateTime(nowTime);
        comment.setDeleted(false);
        int i = commentMapper.insertSelective(comment);
        //更新订单信息

        return i;
    }
}
