package com.cskaoyan.service.promote;

import com.cskaoyan.bean.*;

import java.util.ArrayList;
import java.util.List;

public interface PromoteService<T> {
//    List<T> queryAdList();
//    List<T> queryAdListByName(String name);

    List<T> queryAdByNameAndContent(String name, String content);
    int insertAdList(List<T> adList);
    int updateAdList(List<T> objectList);
    int deleteAdList(List<T> objectList);


    List<T> queryCouponListByNameAndTypeAndStatus(String name, Short type, Short status);
    int insertCouponList(List<T> objectList);
    int updateCouponList(List<T> objectList);
    int deleteCouponList(List<T> couponList);
    List<T> queryCouponReadlList(List<Integer> integers);
    List<T> queryCouponListUser(Integer couponId, Integer userId);

    int insertCoupon2List(List<T> objectList);



    int updateCoupon2List(List<T> objectList);


    List<T> queryTopicListByTitleAndSubtitle(String title, String subtitle);
    int insertTopicList(List<T> objectList);
    int updateTopicList(List<T> objectList);
    int deleteTopicList(List<T> objectList);

    List<T> queryGrouponRulesListByGoodsId(Integer goodsId);
    int insertGrouponRuleList(List<T> objectList);
    int updateGrouponRuleList(List<T> objectList);
    int deleteGrouponRuleList(List<T> objectList);

    List<T> queryGrouponListRecordByGoodsId(Integer goodsId);



}
