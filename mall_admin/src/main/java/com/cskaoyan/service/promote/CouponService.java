package com.cskaoyan.service.promote;

import com.cskaoyan.bean.Ad;
import com.cskaoyan.bean.Coupon;

import java.util.List;

public interface CouponService {
    List<Coupon> queryAdListByName(String name);
}
