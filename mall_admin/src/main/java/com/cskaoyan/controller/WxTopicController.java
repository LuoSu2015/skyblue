package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.Topic;
import com.cskaoyan.bean.WxTopic;
import com.cskaoyan.service.WxTopicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WxTopicController {
    @Autowired
    WxTopicService wxTopicService;

    @RequestMapping("wx/topic/list")
    public BaseRespVo showTopic(Integer page, Integer size) {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();

        PageHelper.startPage(page,size);
        List<Topic> topicList = wxTopicService.getTopicList();
        PageInfo<Topic> pageInfo = new PageInfo(topicList);
        long count = pageInfo.getTotal();

        data.put("count",count);
        data.put("data",topicList);

        baseRespVo.setErrno(0);
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("wx/topic/detail")
    public BaseRespVo topicDetail(String id) {
        Integer topicId = Integer.parseInt(id);
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();

        //goods:[]?   showtype ???
        WxTopic topic = wxTopicService.getTopicById(topicId);
        data.put("topic",topic);
        data.put("goods",topic.getGoods());

        baseRespVo.setErrno(0);
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("wx/topic/related")
    public BaseRespVo relatedTopic(String id) {
        Integer topicId = Integer.parseInt(id);
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();

        //推荐的逻辑？？ 暂根据sort_order 相同来分
        List<WxTopic> data = wxTopicService.getRelatedTopicById(topicId);

        baseRespVo.setErrno(0);
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
