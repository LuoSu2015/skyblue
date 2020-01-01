package com.cskaoyan.service;

import com.cskaoyan.bean.Keyword;

import java.util.List;

public interface WxSearchService {
    Keyword getDefaultKeyword();

    List<Keyword> getHotKeywordList();

    List<Keyword> getHistoryKeywordList(Integer userId);

    String[] getSearchHelp(String keyword);

    void clearHistory();
}
