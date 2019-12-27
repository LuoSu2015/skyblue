package com.cskaoyan.service;


import com.cskaoyan.bean.Brand;
import com.cskaoyan.bean.Keyword;

import java.util.List;
import java.util.Map;

public interface MarketService {

    Map queryKeyword(int page, int limit, String keyword, String url, String sort, String order);

    Keyword insertKeyword(Keyword keyword);

    void deleteKeywordById(int id);

    List<Brand> queryBrand(Integer page, Integer limit, String sort, String order);

    Keyword updateKeyword(Keyword keyword);

}
