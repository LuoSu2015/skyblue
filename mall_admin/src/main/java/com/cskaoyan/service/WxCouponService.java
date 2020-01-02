package com.cskaoyan.service;

import com.cskaoyan.bean.Coupon;
import com.cskaoyan.exception.AdEx;

import java.util.List;
import java.util.Map;

public interface WxCouponService {
    /**
     * 优惠券列表
     * @param map 分页参数
     * @return 所有的优惠券
     */
     List<Coupon> wxCouponList(Map<String, String> map);

    /**
     * 我的优惠券
     * @param map 分页参数
     * @return 指定用户的优惠券
     */
    List<Coupon> couponMylist(Map<String, String> map);

    /**
     * 可用优惠券
     * @param map 购物车id，团购id
     * @return 可用优惠券列表
     */
    List<Coupon> wxCouponSelectlist(Map<String, String> map);

    /**
     * 优惠券领取
     * 通用类型-兑换类型-注册类型
     * 领取-先查再写
     * @param couponId 优惠券ID
     * @return 优惠券是否已经领取
     */
    Integer wxCouponReceive(Integer couponId) throws AdEx;

    /**
     * 优惠券兑换
     * @param coupon 优惠券信息
     * @return 是否领取成功
     */
    Integer wxCouponExchange(Coupon coupon) throws AdEx;
}
