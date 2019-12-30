package com.cskaoyan.mapper;

import com.cskaoyan.bean.Coupon;
import com.cskaoyan.bean.CouponExample;
import java.util.List;

import com.cskaoyan.bean.promate.Coupon2;
import org.apache.ibatis.annotations.Param;

public interface CouponMapper {
    long countByExample(CouponExample example);

    int deleteByExample(CouponExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    int insert2Selective(Coupon2 record);

    List<Coupon> selectByExample(CouponExample example);

    Coupon selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Coupon record, @Param("example") CouponExample example);

    int updateByExample(@Param("record") Coupon record, @Param("example") CouponExample example);

    int updateByPrimaryKeySelective(Coupon record);

    int update2ByPrimaryKeySelective(Coupon2 record);

    int updateByPrimaryKey(Coupon record);
}