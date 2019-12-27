package com.cskaoyan.service.promote.Impl;

import com.cskaoyan.bean.Ad;
import com.cskaoyan.bean.AdExample;
import com.cskaoyan.mapper.AdMapper;
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
            criteria.andNameLike(name);
        }
        if(content!=null){
            criteria.andContentLike(content);
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
}
