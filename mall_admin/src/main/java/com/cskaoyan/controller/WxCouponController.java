package com.cskaoyan.controller;

import com.cskaoyan.bean.Ad;
import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.CouponUser;
import com.cskaoyan.exception.AdEx;
import com.cskaoyan.service.WxCouponServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WxCouponController {

    @Autowired
    WxCouponServiceImpl wxCouponService;
    /**
     * 优惠券列表
     * @param map 分页参数
     * @return 所有的优惠券
     */
    @RequestMapping("wx/coupon/list")
    public BaseRespVo couponList(@RequestParam Map<String,String> map){
        List<Coupon> couponList = wxCouponService.wxCouponList(map);
        PageInfo<Coupon> couponPageInfo = new PageInfo<>(couponList);
        long total = couponPageInfo.getTotal();
        Map map1 = new HashMap();
        map1.put("data",couponList);
        map1.put("count",total);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map1);
        return baseRespVo;
    }

    /**
     * 我的优惠券
     * @param map 分页参数
     * @return 指定用户的优惠券
     */
    @RequestMapping("wx/coupon/mylist")
    public BaseRespVo couponMylist(@RequestParam Map<String,String> map){
        List<Coupon> couponList = wxCouponService.couponMylist(map);
        PageInfo<Coupon> couponPageInfo = new PageInfo<>(couponList);
        long total = couponPageInfo.getTotal();
        Map map1 = new HashMap();
        map1.put("data",couponList);
        map1.put("count",total);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map1);
        return baseRespVo;
    }

    /**
     * 可用优惠券
     * @param map 购物车id，团购id
     * @return 可用优惠券列表
     */
    @RequestMapping("wx/coupon/selectlist")
    public BaseRespVo couponSelectlist(@RequestParam Map<String,String> map) {
        List<Coupon> couponlist = wxCouponService.wxCouponSelectlist(map);
        PageInfo<Coupon> couponPageInfo = new PageInfo<>(couponlist);
        long total = couponPageInfo.getTotal();
        Map map1 = new HashMap();
        map1.put("data",couponlist);
        map1.put("count",total);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map1);
        return baseRespVo;
    }

    /**
     * 优惠券领取
     * 通用类型-兑换类型-注册类型
     * 领取-先查再写
     * @param couponUser 优惠券领取用户
     * @return 是否领取成功
     */
    @RequestMapping("wx/coupon/receive")
    public BaseRespVo couponReceive(@RequestBody CouponUser couponUser) throws AdEx {
        Integer couponReceive = wxCouponService.wxCouponReceive(couponUser.getCouponId());
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        if(couponReceive == 1){
            baseRespVo.setErrmsg("领取优惠券成功");
        }else {
            baseRespVo.setErrno(740);
            baseRespVo.setErrmsg("优惠券已领取过");

        }
        return baseRespVo;
    }

    /**
     * 优惠券兑换
     * @param coupon 优惠券信息
     * @return 是否领取成功
     * @throws AdEx
     */
    @RequestMapping("wx/coupon/exchange")
    public BaseRespVo couponExchange(@RequestBody Coupon coupon) throws AdEx {
        Integer couponExchange = wxCouponService.wxCouponExchange(coupon);
        BaseRespVo baseRespVo = new BaseRespVo();
        if (couponExchange == 1) {
            baseRespVo.setErrmsg("兑换优惠券成功");
            baseRespVo.setErrno(0);
        } else {
            baseRespVo.setErrno(740);
            baseRespVo.setErrmsg("优惠券已领取过");

        }
        return baseRespVo;
    }


}
