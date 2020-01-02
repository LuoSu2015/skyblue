package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Comment;
import com.cskaoyan.bean.Order;
import com.cskaoyan.bean.User;
import com.cskaoyan.bean.wx.order.OrderGoodsReturn;
import com.cskaoyan.service.OrderService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WxOrderController {

    @Autowired
    OrderService orderService;

    /**
     * 501 请登录
     */
    @RequestMapping("wx/order/list")
    public BaseRespVo getOrderList(Integer showType, Integer page, Integer size){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer id = user.getId();
        Map data = orderService.queryOrder(showType,page,size,id);

        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
    @RequestMapping("wx/order/detail")
    public BaseRespVo getGoodDetail(Integer orderId){
        Map map = orderService.queryOrderDetail(orderId);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setData(map);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 提交订单
     * 操作:
     *      订单表,
     *      订单商品关系表
     *      address表
     */
    @RequestMapping("wx/order/submit")
    public BaseRespVo createOrder(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer order = orderService.createOrder(user);
        BaseRespVo baseRespVo = new BaseRespVo();
        Map map = new HashMap();
        map.put("orderId",order);
        baseRespVo.setErrno(0);
//        baseRespVo.setData(map);
        baseRespVo.setData(map);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }


    /**
     * 订单支付页面
     * @param map
     * @return
     */
    @RequestMapping("wx/order/prepay")
    public BaseRespVo prePay(@RequestBody Map map){
        Object orderId1 = map.get("orderId");
        if(orderId1 instanceof String){
            int orderId = Integer.parseInt((String)orderId1);
            orderService.payOrders(orderId);
        }else {
            Integer orderId = (Integer) map.get("orderId");
            orderService.payOrders(orderId);
        }

        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setData(null);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }


    /**
     * 确认收货
     * @param map
     * @return
     */
    @RequestMapping("wx/order/confirm")
    public BaseRespVo confirmOrder(@RequestBody Map map){
        Integer orderId = (Integer) map.get("orderId");
        orderService.orderConfirm(orderId);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return null;
    }



    /**
     * 删除订单
     * ?: 用户取消订单 是否就是 用户 删除订单?
     * 删除逻辑:
     *  1.deleted 设为 1
     *  2.只有未付款, 以及 用户收货 这两种订单状态 可以 删除订单 //不需要
     * @param
     * @return
     */
    @RequestMapping("wx/order/delete")
    public BaseRespVo deleteOrder(@RequestBody Map map){
        //更新字段
        Integer orderId = (Integer) map.get("orderId");
        orderService.deleteOrderById(orderId);
        //返回数据
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 删除订单
     * ?: 用户取消订单 是否就是 用户 删除订单?
     * 取消逻辑:
     *  deleted 设为 1
     * @param
     * @return
     */
    @RequestMapping("wx/order/cancel")
    public BaseRespVo cancelOrder(@RequestBody Map map){
        //更新字段
        Integer orderId = (Integer) map.get("orderId");
        orderService.deleteOrderById(orderId);
        //返回数据
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 显示提交信息页面的商品信息
     * @param goodsId
     * @param orderId
     * @return
     */
    @RequestMapping("wx/order/goods")
    public BaseRespVo goodsOrder(Integer goodsId,Integer orderId){
        OrderGoodsReturn orderGoodsReturn = orderService.selectOrderGoodsReturn(goodsId, orderId);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setData(orderGoodsReturn);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 提交评论
     * @param comment
     * @return
     */
    @RequestMapping("wx/order/comment")
    public BaseRespVo createComment(@RequestBody Comment comment){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        orderService.insertComment(comment,user);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

}
