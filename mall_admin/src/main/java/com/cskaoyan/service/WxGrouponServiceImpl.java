package com.cskaoyan.service;

import com.cskaoyan.bean.Groupon;
import com.cskaoyan.bean.wxgroupon.WxGroupon;
import com.cskaoyan.mapper.GrouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxGrouponServiceImpl implements WxGrouponService {
    @Autowired
    GrouponMapper grouponMapper;

    @Override
    public List<WxGroupon> getGrouponList() {
        List<WxGroupon> grouponList = grouponMapper.getGrouponList();
        return grouponList;
    }
}
