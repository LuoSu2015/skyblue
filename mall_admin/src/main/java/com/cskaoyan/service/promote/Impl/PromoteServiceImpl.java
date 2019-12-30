package com.cskaoyan.service.promote.Impl;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.*;
import com.cskaoyan.service.promote.PromoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoteServiceImpl<T> implements PromoteService<T> {



/*    @Override
    public List<T> queryAdList() {
        AdExample adExample = new AdExample();
//        AdExample.Criteria criteria = adExample.createCriteria();
        List<T> ads = (List<T>) adMapper.selectByExample(adExample);
        return ads;
    }*/
/*    @Override
    public List<T> queryAdListByName(String name) {
        AdExample adExample = new AdExample();
        AdExample.Criteria criteria = adExample.createCriteria();
        if (name != null && name!="") {
            criteria.andNameLike("%"+name+"%");
        }
        List<T> ads = (List<T>) adMapper.selectByExample(adExample);
        return ads;
    }*/

    @Autowired
    AdMapper adMapper;

    @Override
    public List<T> queryAdByNameAndContent(String name, String content) {
        AdExample adExample = new AdExample();
        AdExample.Criteria criteria = adExample.createCriteria();
        if (name != null) {
            criteria.andNameLike("%" + name + "%");
        }
        if (content != null) {
            criteria.andContentLike("%" + content + "%");
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
    public int updateAdList(List<T> objectList) {
        int count_update = 0;
        for (T object : objectList) {
            int i = grouponMapper.updateByPrimaryKeySelective((Groupon) object);
            if (i != 0) {
                count_update++;
            }
        }
        return count_update;
    }

    @Override
    public int deleteAdList(List<T> objectList) {
        int count_delete = 0;
        for (T object : objectList) {
            Integer id = ((Ad) object).getId();
            if (id != null) {
                int i = adMapper.deleteByPrimaryKey(id);
                if (i != 0) {
                    count_delete++;
                }
            }
        }
        return count_delete;
    }

    @Autowired
    CouponMapper couponMapper;

    @Override
    public List<T> queryCouponListByNameAndTypeAndStatus(String name, Short type, Short status) {
        CouponExample couponExample = new CouponExample();
        CouponExample.Criteria criteria = couponExample.createCriteria();
        if (name != null) {
            criteria.andNameLike("%" + name + "%");
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        List<T> couponList = (List<T>) couponMapper.selectByExample(couponExample);
        return couponList;
    }

    @Override
    public int insertCouponList(List<T> objectList) {
        int count_insert = 0;
        for (T coupon : objectList) {
            int i = couponMapper.insertSelective((Coupon) coupon);
            if (i != 0) {
                count_insert++;
            }
        }

        return count_insert;
    }

   /* @Override
    public int insertCoupon2List(List<T> objectList) {
        int count_insert=0;
        for (T coupon2 :objectList) {
            int i = couponMapper.insert2Selective((Coupon2) coupon2);
            if(i!=0){
                count_insert++;
            }
        }

        return count_insert;
    }*/

    @Override
    public int updateCouponList(List<T> objectList) {

        int count_update = 0;
        for (T object : objectList) {
            int i = couponMapper.updateByPrimaryKeySelective((Coupon) object);
            if (i != 0) {
                count_update++;
            }
        }
        return count_update;
    }

 /*   @Override
    public int updateCoupon2List(List<T> objectList) {

        int count_update = 0;
        for (T object : objectList) {
            int i = couponMapper.update2ByPrimaryKeySelective((Coupon2) object);
            if (i != 0) {
                count_update++;
            }
        }
        return count_update;
    }*/

    @Override
    public int deleteCouponList(List<T> objectList) {
        int count_delete = 0;
        for (T object : objectList) {
            Integer id = ((Coupon) object).getId();
            if (id != null) {
                int i = couponMapper.deleteByPrimaryKey(id);
                if (i != 0) {
                    count_delete++;
                }
            }
        }
        return count_delete;
    }

    @Override
    public List<T> queryCouponReadlList(List<Integer> integers) {
        CouponExample couponExample = new CouponExample();
        CouponExample.Criteria criteria = couponExample.createCriteria();
        for (Integer i : integers) {
            if (integers != null) {
                criteria.andIdEqualTo(i);
            }
            List<T> couponList = (List<T>) couponMapper.selectByExample(couponExample);
            return couponList;
        }
        /*因为返回的是一个，我这里先暂时写Null，没有就不能返回*/
        return null;

    }

    /*暂时还没有写业务，因为不知道要传什么内容，先搁浅*/
    @Override
    public List<T> queryCouponListUser(Integer couponId, Integer userId) {
        return null;
    }

    @Autowired
    TopicMapper topicMapper;

    @Override
    public List<T> queryTopicListByTitleAndSubtitle(String title, String subtitle) {
        TopicExample topicExample = new TopicExample();
        TopicExample.Criteria criteria = topicExample.createCriteria();
        if (title != null) {
            criteria.andTitleLike("%" + title + "%");
        }
        if (subtitle != null) {
            criteria.andSubtitleLike("%" + subtitle + "%");
        }
        List<T> topicList = (List<T>) topicMapper.selectByExample(topicExample);
        return topicList;
    }

    @Override
    public int insertTopicList(List<T> objectList) {
        int count_insert = 0;
        for (T object : objectList) {
            count_insert = topicMapper.insert((Topic) object);
            if (count_insert != 0) {
                count_insert++;
            }
        }
        return count_insert;
    }

    @Override
    public int updateTopicList(List<T> objectList) {
        int count_update = 0;
        for (T object : objectList) {
            int i = topicMapper.updateByPrimaryKeySelective((Topic) object);
            if (i != 0) {
                count_update++;
            }
        }
        return count_update;
    }

    @Override
    public int deleteTopicList(List<T> objectList) {
        int count_delete = 0;
        for (T object : objectList) {
            Integer id = ((Topic) object).getId();
            if (id != null) {
                int i = topicMapper.deleteByPrimaryKey(id);
                if (i != 0) {
                    count_delete++;
                }
            }
        }
        return count_delete;
    }

    @Autowired
    Groupon_rulesMapper groupon_rulesMapper;

    @Override
    public List<T> queryGrouponRulesListByGoodsId(Integer goodsId) {
        Groupon_rulesExample grouponRulesExample = new Groupon_rulesExample();
        Groupon_rulesExample.Criteria criteria = grouponRulesExample.createCriteria();
        if (goodsId != null) {
            criteria.andGoodsIdEqualTo(goodsId);
            List<T> groupon_rulesList = (List<T>) groupon_rulesMapper.selectByExample(grouponRulesExample);
            return groupon_rulesList;
        }
        List<T> groupon_rulesList = (List<T>) groupon_rulesMapper.selectByExample(grouponRulesExample);
        return groupon_rulesList;
    }

    @Autowired
    GrouponMapper grouponMapper;

    @Override
    public List<T> queryGrouponListRecordByGoodsId(Integer goodsId) {
        GrouponExample grouponExample = new GrouponExample();
        GrouponExample.Criteria criteria = grouponExample.createCriteria();
        if (goodsId != null) {
            criteria.andIdEqualTo(goodsId);
            List<T> grouponList = (List<T>) grouponMapper.selectByExample(grouponExample);
            return grouponList;
        }
        List<T> grouponList = (List<T>) grouponMapper.selectByExample(grouponExample);
        return grouponList;
    }

    @Autowired
    StorageMapper storageMapper;

    @Override
    public int insertStorageList(List<T> storageList) {
        int count_insert = 0;
        for (T storage : storageList) {
            int i = storageMapper.insertSelective((Storage) storage);
            if (i != 0) {
                count_insert++;
            }
        }
        return count_insert;
    }


    @Override
    public int insertGrouponRuleList(List<T> objectList) {
        int count_insert = 0;
        for (T object : objectList) {
            count_insert = groupon_rulesMapper.insert((Groupon_rules) object);
            if (count_insert != 0) {
                count_insert++;
            }
        }
        return count_insert;
    }

    @Override
    public int updateGrouponRuleList(List<T> objectList) {
        int count_update = 0;
        for (T object : objectList) {
            int i = groupon_rulesMapper.updateByPrimaryKeySelective((Groupon_rules) object);
            if (i != 0) {
                count_update++;
            }
        }
        return count_update;
    }

    @Override
    public int deleteGrouponRuleList(List<T> objectList) {
        int count_delete = 0;
        for (T object : objectList) {
            Integer id = ((Groupon_rules) object).getId();
            if (id != null) {
                int i = groupon_rulesMapper.deleteByPrimaryKey(id);
                if (i != 0) {
                    count_delete++;
                }
            }
        }
        return count_delete;
    }
}
