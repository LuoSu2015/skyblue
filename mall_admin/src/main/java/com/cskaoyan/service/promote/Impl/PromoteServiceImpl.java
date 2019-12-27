package com.cskaoyan.service.promote.Impl;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.*;
import com.cskaoyan.service.promote.PromoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoteServiceImpl<T> implements PromoteService<T>{

    @Autowired
    AdMapper adMapper;

    @Override
    public List<T> queryAdList() {
        AdExample adExample = new AdExample();
//        AdExample.Criteria criteria = adExample.createCriteria();
        List<T> ads = (List<T>) adMapper.selectByExample(adExample);
        return ads;
    }

    @Override
    public List<T> queryAdByNameAndContent(String name, String content) {
        AdExample adExample = new AdExample();
        AdExample.Criteria criteria = adExample.createCriteria();
        if(name!=null){
            criteria.andNameLike("%"+name+"%");
        }
        if(content!=null){
            criteria.andContentLike("%"+content+"%");
        }
        List<T> ads = (List<T>) adMapper.selectByExample(adExample);
        return ads;
    }



    @Override
    public int insertAdList(List<T> adList) {
        int count_insert = 0;
        for (T object : adList) {
            count_insert = adMapper.insert((Ad) object);
            if (count_insert != 0) {
                count_insert++;
            }
        }
        return count_insert;
    }

    @Override
    public List<T> queryAdListByName(String name) {
        AdExample adExample = new AdExample();
        AdExample.Criteria criteria = adExample.createCriteria();
        if (name != null && name!="") {
            criteria.andNameLike("%"+name+"%");
        }
        List<T> ads = (List<T>) adMapper.selectByExample(adExample);
        return ads;
    }

    @Autowired
    CouponMapper couponMapper;

    @Override
    public List<T> queryCouponListByNameAndTypeAndStatus(String name, Short type, Short status) {
        CouponExample couponExample = new CouponExample();
        CouponExample.Criteria criteria = couponExample.createCriteria();
        if(name!=null){
            criteria.andNameLike("%"+name+"%");
        }
        if(type!=null){
            criteria.andTypeEqualTo(type);
        }
        List<T> couponList = (List<T>) couponMapper.selectByExample(couponExample);
        return couponList;
    }

    @Autowired
    TopicMapper topicMapper;
    @Override
    public List<T> queryTopicListByTitleAndSubtitle(String title, String subtitle) {
        TopicExample topicExample = new TopicExample();
        TopicExample.Criteria criteria = topicExample.createCriteria();
        if(title!=null){
            criteria.andTitleLike("%"+title+"%");
        }
        if(subtitle!=null){
            criteria.andSubtitleLike("%"+subtitle+"%");
        }
        List<T> topicList = (List<T>) topicMapper.selectByExample(topicExample);
        return topicList;
    }

    @Autowired
    Groupon_rulesMapper groupon_rulesMapper;
    @Override
    public List<T> queryGrouponRulesListByGoodsId(Integer goodsId) {
        Groupon_rulesExample grouponRulesExample = new Groupon_rulesExample();
        Groupon_rulesExample.Criteria criteria = grouponRulesExample.createCriteria();
        if(goodsId!=null){
            criteria.andIdEqualTo(goodsId);
            List<T> groupon_rulesList = (List<T>) groupon_rulesMapper.selectByExample(grouponRulesExample);
            return groupon_rulesList;
        }
        return null;
    }

    @Autowired
    GrouponMapper grouponMapper;

    @Override
    public List<T> queryGrouponListRecordByGoodsId(Integer goodsId) {
        GrouponExample grouponExample = new GrouponExample();
        GrouponExample.Criteria criteria = grouponExample.createCriteria();
        if(goodsId!=null){
            criteria.andIdEqualTo(goodsId);
            List<T> grouponList = (List<T>) grouponMapper.selectByExample(grouponExample);
            return grouponList;
        }

        return null;
    }
}
