package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Keyword;
import com.cskaoyan.bean.User;
import com.cskaoyan.service.WxSearchService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WxSearchController {
    @Autowired
    WxSearchService wxSearchService;

    @RequestMapping("wx/search/index")
    public BaseRespVo showSearch(){
        //获取shiro的userId
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer userId = user.getId();

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();

        Keyword defaultKeyword = wxSearchService.getDefaultKeyword();
        List<Keyword> hotKeywordList = wxSearchService.getHotKeywordList();
        List<Keyword> historyKeywordList = null;
        if (userId != null){
            historyKeywordList = wxSearchService.getHistoryKeywordList(userId);
        }else {
            historyKeywordList = wxSearchService.getHistoryKeywordList(-1);
        }
        data.put("defaultKeyword",defaultKeyword);
        data.put("hotKeywordList",hotKeywordList);
        data.put("historyKeywordList",historyKeywordList);

        baseRespVo.setErrno(0);
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("wx/search/helper")
    public BaseRespVo searchHelp(@RequestParam String keyword){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        String[] data = wxSearchService.getSearchHelp(keyword);

        baseRespVo.setErrno(0);
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("wx/search/clearhistory")
    public BaseRespVo clearHistory(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();

        wxSearchService.clearHistory();

        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
