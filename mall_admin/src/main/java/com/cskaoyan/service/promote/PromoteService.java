package com.cskaoyan.service.promote;

import com.cskaoyan.bean.Groupon;
import com.cskaoyan.bean.Groupon_rules;
import com.cskaoyan.bean.Topic;

import java.util.List;

public interface PromoteService<T> {
    List<T> queryAdList();

    List<T> queryAdByNameAndContent(String name, String content);

    int insertAdList(List<T> adList);

    List<T> queryAdListByName(String name);

    List<T> queryCouponListByNameAndTypeAndStatus(String name, Short type, Short status);

    List<T> queryTopicListByTitleAndSubtitle(String title, String subtitle);

    List<T> queryGrouponRulesListByGoodsId(Integer goodsId);

    List<T> queryGrouponListRecordByGoodsId(Integer goodsId);
}
