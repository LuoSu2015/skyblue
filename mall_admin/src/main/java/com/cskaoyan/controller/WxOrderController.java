package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.User;
import com.cskaoyan.service.OrderService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public BaseRespVo createOrder(Integer cartId, Integer addressId, Integer couponId, String message,Integer grouponRulesId, Integer grouponLinkId){
        boolean flag = orderService.createOrder(cartId, addressId, couponId, message, grouponRulesId,  grouponLinkId);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
//        baseRespVo.setData(map);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }


    /**
     * 订单支付页面
     * @param map
     * @return
     */
    @RequestMapping("wx/order/prepay")
    public BaseRespVo prePay(@PathVariable Map map){
        Integer orderId = (Integer) map.get("orderId");
        return null;
    }


    /**
     * 确认收货
     * @param map
     * @return
     */
    @RequestMapping("wx/order/confirm")
    public BaseRespVo confirmOrder(@PathVariable Map map){
        Integer orderId = (Integer) map.get("orderId");
        return null;
    }



    /**
     * 删除订单
     * ?: 用户取消订单 是否就是 用户 删除订单?
     * 删除逻辑:
     *  1.deleted 设为 1
     *  2.只有未付款, 以及 用户收货 这两种订单状态 可以 删除订单 //不需要
     * @param orderId
     * @return
     */
    @RequestMapping("wx/order/delete")
    public BaseRespVo deleteOrder(Integer orderId){
        BaseRespVo baseRespVo = new BaseRespVo();
        return baseRespVo;
    }


}
