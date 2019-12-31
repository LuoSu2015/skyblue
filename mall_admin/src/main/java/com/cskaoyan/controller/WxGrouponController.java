package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.User;
import com.cskaoyan.service.WxGrouponService;
import com.cskaoyan.service.WxGrouponServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WxGrouponController {
    @Autowired
    WxGrouponServiceImpl wxGrouponService;

    /**
     * 微信小程序团购专区团购列表功能
     * @param page 当前页数
     * @param size 分页条目数
     * @return 全部的团购数据
     */
    @RequestMapping("wx/groupon/list")
    public BaseRespVo grouponlist(Integer page,Integer size){
        List<Map> grouponlist = wxGrouponService.grouponlist(page, size);
        Map map = new HashMap();
        map.put("data",grouponlist);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setData(map);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 微信小程序个人详情页我的团购模块功能
     * @param showType 确认团购状态的状态码
     * @return user个人的团购信息
     */
    @RequestMapping("groupon/my")
    public BaseRespVo grouponMy(@RequestParam Integer showType) {
        Map<String, Object> grouponMy = wxGrouponService.wxGrouponMy(showType);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        baseRespVo.setData(grouponMy);
        return baseRespVo;
    }

}
