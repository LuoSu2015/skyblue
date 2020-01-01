package com.cskaoyan.service;


import com.cskaoyan.bean.wxgroupon.WxGroupon;
import java.util.List;
import java.util.Map;

public interface WxGrouponService {
    List<WxGroupon> getGrouponList();

    /**
     * 微信小程序团购专区团购列表功能
     * @param page 当前页数
     * @param size 分页条目数
     * @return 全部的团购数据
     */
    List<Map> grouponlist(Integer page, Integer size);

    /**
     * 微信小程序个人详情页我的团购模块功能
     * @param showType 确认团购状态的状态码
     * @return user个人的团购信息
     */
    Map<String, Object> wxGrouponMy(Integer showType);

    /**
     * 微信小程序团购详情
     * @param grouponId 团购ID
     * @return user个人的团购信息
     */
    Map<String, Object> wxGrouponDetail(Integer grouponId);

}
