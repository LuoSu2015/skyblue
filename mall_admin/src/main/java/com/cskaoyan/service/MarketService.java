package com.cskaoyan.service;


import com.cskaoyan.bean.*;

import java.util.List;
import java.util.Map;

public interface MarketService {

    Map queryKeyword(int page, int limit, String keyword, String url, String sort, String order);

    Keyword insertKeyword(Keyword keyword);

    Keyword deleteKeyword(Keyword keyword);

    Map queryBrand(Integer page,Integer limit,String sort,String order,Integer id,String name);

    Keyword updateKeyword(Keyword keyword);

    int insertStorage(Storage storage);

    Brand insertBrand(BrandSpare brandSpare);

    Brand deleteBrand(Brand brand);

    int updateBreand(Brand brand);

    Map queryIssue(Integer page,Integer limit,String sort,String order,String question);

    Issue insertIssue(Issue issue);

    int updateIssue(Issue issue);

    Issue delectIssue(Issue issue);

    List<CategorySpare> queryCategory();

    List<Map> queryCategoryByL1();

    Category insertCategory(Category category);

    int updateCategroy(Category category);

    Category delectCategroy(Category category);

    Map queryOrders(Integer page,Integer limit,String sort,String order,Integer userId,Short[] orderStatusArray,String orderSn);

    List<OrderGoods> queryGoods(Integer id);
}
