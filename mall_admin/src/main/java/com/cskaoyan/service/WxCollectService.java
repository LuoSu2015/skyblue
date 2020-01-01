package com.cskaoyan.service;

import com.cskaoyan.bean.WxMyCollect;

import java.util.List;

public interface WxCollectService {
    List<WxMyCollect> getGoodsCollectByUserId(Integer userId);

    String addOrDeleteCollect(Integer type, Integer valueId, Integer userId);

}
