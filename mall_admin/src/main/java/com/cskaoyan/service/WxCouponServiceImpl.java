package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.exception.AdEx;
import com.cskaoyan.exception.ExceptionControllerAdvise;
import com.cskaoyan.mapper.CartMapper;
import com.cskaoyan.mapper.CouponMapper;
import com.cskaoyan.mapper.CouponUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WxCouponServiceImpl implements  WxCouponService{
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    CouponUserMapper couponUserMapper;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    ExceptionControllerAdvise exceptionControllerAdvise;

    /**
     * 优惠券列表
     * @param map 分页参数
     * @return 所有的优惠券
     */
    @Override
    public List<Coupon> wxCouponList(Map<String, String> map) {
        Integer page = Integer.parseInt(map.get("page"));
        Integer limit = Integer.parseInt(map.get("limit"));
        PageHelper.startPage(page, limit);

        CouponExample couponExample = new CouponExample();
        //OrderByClause对应排序方式
        couponExample.setOrderByClause(map.get("sort") + " " + map.get("order"));
        //搜索条件（与）
        //判断有没有请求此key
        CouponExample.Criteria criteria = couponExample.createCriteria();
        if (map.containsKey("name")) {
            criteria.andNameLike("%" + map.get("name") + "%");
        }
        if (map.containsKey("status")) {
            criteria.andStatusEqualTo(Short.valueOf(map.get("status")));
        }
        if (map.containsKey("type")) {
            criteria.andTypeEqualTo(Short.valueOf(map.get("type")));
        }
        List<Coupon> couponList = couponMapper.selectByExample(couponExample);
        return couponList;
    }

    /**
     * 我的优惠券
     * @param map 分页参数
     * @return 指定用户的优惠券
     */
    @Override
    public List<Coupon> couponMylist(Map<String, String> map) {
        Integer page = Integer.parseInt(map.get("page"));
        Integer size = Integer.parseInt(map.get("size"));
        PageHelper.startPage(page, size);
        //获取当前用户信息
        User user = user1();
        String s = user.toString();
        //获得当前用户优惠券关系表
        CouponUserExample couponUserExample = new CouponUserExample();
        couponUserExample.createCriteria()
                .andStatusEqualTo(Short.valueOf(map.get("status")))
                .andUserIdEqualTo(user.getId());
        List<CouponUser> couponUserList = couponUserMapper.selectByExample(couponUserExample);
        //查询
        List<Coupon> list = new ArrayList<>();
        for (CouponUser couponUser:couponUserList) {
            CouponExample couponExample = new CouponExample();
            CouponExample.Criteria criteria = couponExample.createCriteria();
            criteria.andIdEqualTo(couponUser.getCouponId());
            List<Coupon> coupons = couponMapper.selectByExample(couponExample);
            coupons.get(0).setStartTime(couponUser.getStartTime());
            coupons.get(0).setEndTime(couponUser.getEndTime());
            if(coupons.size() != 0){
                list.add(coupons.get(0));
            }
        }
        return list;
    }

    /**
     * 可用优惠券
     * @param map 购物车id，团购id
     * @return 可用优惠券列表
     */
    @Override
    public List<Coupon> wxCouponSelectlist(Map<String, String> map) {
        //获取当前时间
        Date time = timeNow();
        //获取当前用户信息
        User user = user1();
        Double aDouble = (double)0;
        if(Integer.valueOf(map.get("cartId")) == 0){
            aDouble = cartMapper.checkedGoodsAmount(user.getId());
        }else if(Integer.valueOf(map.get("cartId")) > 0){
            Cart cart = cartMapper.selectByPrimaryKey(Integer.valueOf(map.get("cartId")));
            aDouble = cart.getPrice().doubleValue() * cart.getNumber().doubleValue();
        }
        //GrouponRules grouponRules = grouponRulesMapper.selectByPrimaryKey(Integer.valueOf(map.get("grouponRulesId")));
        //if(map.get("grouponRulesId").equals("0")){
        CouponUserExample couponUserExample = new CouponUserExample();
        couponUserExample.createCriteria().andUserIdEqualTo(user.getId())
                .andStatusEqualTo((short) 0);
        //该用户未过期购物券
        List<CouponUser> couponUserList = couponUserMapper.selectByExample(couponUserExample);
        List<Coupon> list = new ArrayList<>();
        for (CouponUser couponUser:couponUserList) {
            CouponExample couponExample = new CouponExample();
            couponExample.createCriteria().andIdEqualTo(couponUser.getCouponId());
            //符合要求购物券本体
            List<Coupon> couponList = couponMapper.selectByExample(couponExample);
            for (Coupon coupon:couponList) {
                Calendar timeAgo = Calendar.getInstance();
                timeAgo.setTime(time);
                timeAgo.add(Calendar.DAY_OF_YEAR,-coupon.getDays());
                Date time1 = timeAgo.getTime();
                if(coupon.getTimeType()== 0 && time1.before(couponUser.getAddTime()) && coupon.getMin().doubleValue() < aDouble){//天数
                    coupon.setStartTime(couponUser.getStartTime());
                    coupon.setEndTime(couponUser.getEndTime());
                    list.add(coupon);
                }else if(coupon.getTimeType() == 1 && coupon.getEndTime().after(time) && coupon.getMin().doubleValue() < aDouble){//时间范围
                    list.add(coupon);
                }
            }
        }
        return list;
    }

    /**
     * 优惠券领取
     * 通用类型-兑换类型-注册类型
     * 领取-先查再写
     * @param couponId 优惠券ID
     * @return 优惠券是否已经领取
     */
    @Override
    public Integer wxCouponReceive(Integer couponId) throws AdEx {
        Date time = timeNow();
        //获取当前用户信息
        User user = user1();

        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        //获取当前用户优惠券已领取张数
        long total = limit1(user,couponId, couponUserMapper);
        //判断已领取数小于限领数
        if(coupon.getStatus() == 0){
            if(total < coupon.getLimit()){
                if(coupon.getType() == 0){//通用
                    CouponUser couponUser = couponUserSet(user,coupon, couponId, time);

                    int insert = couponUserMapper.insert(couponUser);
                    if(insert == 1){
                        updateCoupon(time,coupon,couponMapper,couponUserMapper);
                    }
                    return insert;
                }else if(coupon.getType() == 1){//兑换
                    throw new AdEx("店家不定时发放");
                }else{//注册十天内领
                    //session.getAttribute("user")
                    //获取用户注册时间
                    Date addTime = user.getAddTime();
                    //获取当前日期3天前
                    Calendar timeAgo = Calendar.getInstance();
                    timeAgo.setTime(time);
                    timeAgo.add(Calendar.DAY_OF_YEAR,-3);//当前日期3天前

                    if(addTime.after(timeAgo.getTime())){
                        CouponUser couponUser = couponUserSet(user,coupon, couponId, time);
                        int insert = couponUserMapper.insert(couponUser);
                        if(insert == 1){
                            updateCoupon(time,coupon,couponMapper,couponUserMapper);
                        }
                        return insert;
                    }else{
                        throw new AdEx("老用户不能领取");
                    }
                }
            }else{
                throw new AdEx("已达到领取上限");
            }
        }else{
            throw new AdEx("优惠券抢光下架");
        }
    }

    /**
     * 优惠券兑换
     * @param coupon1 优惠券信息
     * @return 是否领取成功
     */
    @Override
    public Integer wxCouponExchange(Coupon coupon1) throws AdEx {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fd = sdf.format(new Date());
        Date time = null;
        try {
            time = sdf.parse(fd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //兑换码所在商品
        CouponExample couponExample = new CouponExample();
        couponExample.createCriteria().andCodeEqualTo(coupon1.getCode());
        List<Coupon> coupons = couponMapper.selectByExample(couponExample);
        if(coupons.size() == 0){
            throw new AdEx("兑换码错误QAQ");
        }
        Coupon coupon = coupons.get(0);
        //获取当前用户信息
        User user = user1();
        //获取当前用户优惠券已领取张数
        long total = limit1(user,coupon.getId(), couponUserMapper);
        //判断已领取数小于限领数
        if(coupon.getStatus() == 0){
            if(total < coupon.getLimit()) {
                CouponUser couponUser = couponUserSet(user, coupon, coupon.getId(), time);
                int insert = couponUserMapper.insert(couponUser);
                if(insert == 1){
                    updateCoupon(time,coupon,couponMapper,couponUserMapper);
                }
                return insert;
            }else{
                throw new AdEx("已达到领取上限");
            }
        }else{
            throw new AdEx("优惠券在抢光后自动下架了");
        }
    }

    public static Date timeNow(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fd = sdf.format(new Date());
        Date time = null;
        try {
            time = sdf.parse(fd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
    public static User user1(){
        //获取当前用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return user;
    }
    public static long limit1(User user,Integer couponId,CouponUserMapper couponUserMapper){
        //获取当前用户优惠券已领取张数
        CouponUserExample couponUserExample = new CouponUserExample();
        CouponUserExample.Criteria criteria = couponUserExample.createCriteria();
        criteria.andUserIdEqualTo(user.getId()).andCouponIdEqualTo(couponId);
        List<CouponUser> couponUserList = couponUserMapper.selectByExample(couponUserExample);
        PageInfo<CouponUser> couponUserPageInfo = new PageInfo<>(couponUserList);
        long total = couponUserPageInfo.getTotal();
        return total;
    }
    public static CouponUser couponUserSet(User user,Coupon coupon, Integer couponId, Date time){
        CouponUser couponUser = new CouponUser();
        couponUser.setUserId(user.getId());
        couponUser.setCouponId(coupon.getId());
        if(coupon.getTimeType() == 1){
            couponUser.setStartTime(coupon.getStartTime());
            couponUser.setEndTime(coupon.getEndTime());
        }
        couponUser.setStartTime(time);

        Calendar timeAgo = Calendar.getInstance();
        timeAgo.setTime(time);
        timeAgo.add(Calendar.DAY_OF_YEAR,coupon.getDays());

        couponUser.setEndTime(timeAgo.getTime());
        couponUser.setStatus((short) 0);
        couponUser.setAddTime(time);
        return couponUser;
    }
    //一天86400000毫秒
    public static void updateCoupon(Date time,Coupon coupon,CouponMapper couponMapper,CouponUserMapper couponUserMapper){
        int lastId = couponUserMapper.selectLastInsertId();
        CouponUser couponUser1 = couponUserMapper.selectByPrimaryKey(lastId);
        Coupon coupon1 = couponMapper.selectByPrimaryKey(couponUser1.getCouponId());
        Integer total1 = coupon1.getTotal();
        if(total1 > 1){
            coupon1.setTotal(total1-1);
            coupon1.setUpdateTime(time);
        }else if(total1 == 1){
            coupon.setStatus((short) 2);
            coupon1.setUpdateTime(time);
        }
        int update = couponMapper.updateByPrimaryKey(coupon1);
    }
}
