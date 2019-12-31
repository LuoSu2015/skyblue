package com.cskaoyan.service;

import com.cskaoyan.bean.Keyword;
import com.cskaoyan.mapper.SearchHistoryMapper;
import com.cskaoyan.mapper.SearchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxSearchServiceImpl implements WxSearchService{
    @Autowired
    SearchMapper searchMapper;

    @Override
    public Keyword getDefaultKeyword() {
        Keyword defaultKeyword = searchMapper.getDefaultKeyword();
        return defaultKeyword;
    }

    @Override
    public List<Keyword> getHotKeywordList() {
        List<Keyword> hotKeywordList = searchMapper.getHotKeywordList();
        return hotKeywordList;
    }

    @Override
    public List<Keyword> getHistoryKeywordList(Integer userId) {
        List<Keyword> historyKeywordList = searchMapper.getHistoryKeywordList(userId);
        return historyKeywordList;
    }

    @Override
    public String[] getSearchHelp(String keyword) {
        String[] data = searchMapper.getHotKeywordContainKey(keyword);
        return data;
    }

    @Override
    public void clearHistory() {
        searchMapper.deleteHistory();
    }
}
