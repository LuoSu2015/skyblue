package com.cskaoyan.service;

import com.cskaoyan.bean.Collect;
import com.cskaoyan.bean.WxMyCollect;
import com.cskaoyan.mapper.CollectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxCollectServiceImpl implements WxCollectService{

    @Autowired
    CollectMapper collectMapper;

    @Override
    public List<WxMyCollect> getGoodsCollectByUserId(Integer userId) {
        List<WxMyCollect> collectList = collectMapper.selectGoodsCollectByUserId(userId);
        return collectList;
    }

    @Override
    public String addOrDeleteCollect(Integer type, Integer valueId, Integer userId) {
        Collect collect = collectMapper.getCollect(type,valueId,userId);
        if (collect != null){
            collectMapper.reverseDeleted(type,valueId,userId,collect.getDeleted());
            if (!collect.getDeleted()){
                return "delete";
            }else {
                return "add";
            }
        }else {
            collectMapper.insertCollect(type,valueId,userId);
            return "add";
        }
    }
}
