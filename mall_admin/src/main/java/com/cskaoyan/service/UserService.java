package com.cskaoyan.service;

import com.cskaoyan.bean.*;

import java.util.List;

public interface UserService {

    /**
     * 用户管理模块会员管理功能首页显示
     * @param page 分页的当前页数
     * @param limit 分页的数据条目数
     * @return 全部的User
     */
    List<User> listuser(int page,int limit,String username,String mobile,String sort,String order);

    /**
     * 用户管理模块收货地址功能首页显示和查询具体User地址接口
     * @param page 分页的当前页数
     * @param limit 分页的数据条目数
     * @param userId 用户ID
     * @param name 用户名
     * @param sort 排序列名
     * @param order 升序或者降序排列
     * @return 符合条件的全部地址信息
     */
    List<Adress> listaddress(int page, int limit, Integer userId, String name,String sort,String order);

    /**
     * 用户管理模块会员收藏功能首页显示和查询具体User收藏商品接口
     * @param page 分页的当前页数
     * @param limit 分页的数据条目数
     * @param userId 用户ID
     * @param valueId 商品ID
     * @param sort 排序列名
     * @param order 升序或者降序排列
     * @return 符合条件的全部商品收藏信息
     */
    List<Collect> listcollect(int page, int limit, Integer userId, Integer valueId,String sort,String order);

    /**
     *用户管理模块会员足迹功能首页显示及具体User浏览信息查询接口
     * @param page 分页的当前页数
     * @param limit 分页的数据条目数
     * @param userId 用户ID
     * @param goodsId 商品ID
     * @param sort 排序列名
     * @param order 升序或者降序排列
     * @return 符合条件的全部商品浏览信息
     */
    List<Footprint> listfootprint(int page, int limit, Integer userId, Integer goodsId, String sort, String order);

    /**
     *用户管理模块搜索历史功能首页显示及具体User浏览信息查询接口
     * @param page 分页的当前页数
     * @param limit 分页的数据条目数
     * @param userId 用户ID
     * @param keyword 关键字
     * @param sort 排序列名
     * @param order 升序或者降序排列
     * @return 符合条件的全部浏览历史信息
     */
    List<SearchHistory> listhistory(int page, int limit, Integer userId, String keyword, String sort, String order);

    /**
     * 用户管理模块意见反馈功能首页显示及具体User反馈信息查询接口
     * @param page 分页的当前页数
     * @param limit 分页的数据条目数
     * @param id 反馈ID
     * @param username 反馈的用户名
     * @param sort 排序列名
     * @param order 升序或者降序排列
     * @return 符合条件的全部User反馈信息
     */
    List<Feedback> listfeedback(int page, int limit, Integer id, String username, String sort, String order);
}
