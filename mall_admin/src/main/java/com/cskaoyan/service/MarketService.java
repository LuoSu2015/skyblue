package com.cskaoyan.service;

import com.cskaoyan.bean.Keyword;
import com.cskaoyan.bean.KeywordExample;
import com.cskaoyan.bean.Brand;

import java.util.List;

public interface MarketService {

    List<Keyword> queryKeyword(int page, int limit, String keyword, String url, String sort, String order);

    Keyword insertKeyword(Keyword keyword);

    void deleteKeywordById(int id);

    List<Brand> queryBrand(Integer page, Integer limit, String sort, String order);

    void updateKeyword(Keyword keyword);

}
