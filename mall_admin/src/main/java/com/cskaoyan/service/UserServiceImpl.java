package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    UserExample userExample = new UserExample();

    /**
     * 用户管理模块会员管理功能首页显示及指定查询
     * @param page 分页的当前页数
     * @param limit 分页的数据条目数
     * @param username 用户名
     * @param mobile 用户手机号
     * @param sort 排序列名
     * @param order 升序或者降序排列
     * @return 符合条件的全部User
     */
    @Override
    public List<User> listuser(int page, int limit,String username,String mobile,String sort,String order) {
        PageHelper.startPage(page,limit);
        String orderby = sort + " " + order;
        PageHelper.orderBy(orderby);
        if (username != null && mobile != null){
            userExample.createCriteria().andUsernameLike(username).andMobileEqualTo(mobile);
        }else if (username != null){
            userExample.createCriteria().andUsernameLike(username);
        }else if (mobile != null){
            userExample.createCriteria().andMobileEqualTo(mobile);
        }
        List<User> userList = userMapper.selectByExample(userExample);
        return userList;
    }

    @Autowired
    AdressMapper adressMapper;
    AdressExample adressExample = new AdressExample();

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
    @Override
    public List<Adress> listaddress(int page, int limit, Integer userId, String name,String sort,String order) {
        PageHelper.startPage(page,limit);
        String orderby = sort + " " + order;
        PageHelper.orderBy(orderby);
        if (userId != null && name != null){
            adressExample.createCriteria().andUserIdEqualTo(userId).andNameLike(name);
        }else if (userId != null){
            adressExample.createCriteria().andUserIdEqualTo(userId);
        }else if (name != null){
            adressExample.createCriteria().andNameLike(name);
        }
        List<Adress> adressList = adressMapper.selectByExample(adressExample);
        return adressList;
    }


    @Autowired
    CollectMapper collectMapper;

    CollectExample collectExample = new CollectExample();

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
    @Override
    public List<Collect> listcollect(int page, int limit, Integer userId, Integer valueId,String sort,String order) {
        PageHelper.startPage(page,limit);
        String orderby = sort + " " + order;
        PageHelper.orderBy(orderby);
        if (userId != null && valueId != null){
            collectExample.createCriteria().andUserIdEqualTo(userId).andValueIdEqualTo(valueId);
        }else if (userId != null){
            collectExample.createCriteria().andUserIdEqualTo(userId);
        }else if (valueId != null){
            collectExample.createCriteria().andValueIdEqualTo(valueId);
        }
        List<Collect> collectList = collectMapper.selectByExample(collectExample);
        return collectList;
    }

    @Autowired
    FootprintMapper footprintMapper;
    FootprintExample footprintExample = new FootprintExample();

    /**
     * 用户管理模块会员足迹功能首页显示及具体User浏览信息查询接口
     * @param page 分页的当前页数
     * @param limit 分页的数据条目数
     * @param userId 用户ID
     * @param goodsId 商品ID
     * @param sort 排序列名
     * @param order 升序或者降序排列
     * @return 符合条件的全部商品浏览信息
     */

    @Override
    public List<Footprint> listfootprint(int page, int limit, Integer userId, Integer goodsId, String sort, String order) {
        PageHelper.startPage(page,limit);
        String orderby = sort + " " + order;
        PageHelper.orderBy(orderby);
        if (userId != null && goodsId != null){
            footprintExample.createCriteria().andUserIdEqualTo(userId).andGoodsIdEqualTo(goodsId);
        }else if (userId != null){
            footprintExample.createCriteria().andUserIdEqualTo(userId);
        }else if (goodsId != null){
            footprintExample.createCriteria().andGoodsIdEqualTo(goodsId);
        }
        List<Footprint> footprintList = footprintMapper.selectByExample(footprintExample);
        return  footprintList;
    }

    @Autowired
    SearchHistoryMapper searchHistoryMapper;
    SearchHistoryExample searchHistoryExample = new SearchHistoryExample();
    /**
     * 用户管理模块搜索历史功能首页显示及具体User浏览信息查询接口
     * @param page 分页的当前页数
     * @param limit 分页的数据条目数
     * @param userId 用户ID
     * @param keyword 关键字
     * @param sort 排序列名
     * @param order 升序或者降序排列
     * @return 符合条件的全部浏览历史信息
     */
    @Override
    public List<SearchHistory> listhistory(int page, int limit, Integer userId, String keyword, String sort, String order) {
        PageHelper.startPage(page,limit);
        String orderby = sort + " " + order;
        PageHelper.orderBy(orderby);
        if (userId != null && keyword != null){
            searchHistoryExample.createCriteria().andUserIdEqualTo(userId).andKeywordLike(keyword);
        }else if (userId != null){
            searchHistoryExample.createCriteria().andUserIdEqualTo(userId);
        }else if(keyword != null){
            searchHistoryExample.createCriteria().andKeywordLike(keyword);
        }
        List<SearchHistory> searchHistoryList = searchHistoryMapper.selectByExample(searchHistoryExample);
        return searchHistoryList;
    }

    @Autowired
    FeedbackMapper feedbackMapper;
    FeedbackExample feedbackExample = new FeedbackExample();
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
    @Override
    public List<Feedback> listfeedback(int page, int limit, Integer id, String username, String sort, String order) {
        PageHelper.startPage(page,limit);
        String orderby = sort + " " + order;
        PageHelper.orderBy(orderby);
        if (id != null && username != null){
            feedbackExample.createCriteria().andIdEqualTo(id).andUsernameLike(username);
        }else if (id != null){
            feedbackExample.createCriteria().andIdEqualTo(id);
        }else if (username != null){
            feedbackExample.createCriteria().andUsernameLike(username);
        }
        List<Feedback> feedbackList = feedbackMapper.selectByExample(feedbackExample);
        return feedbackList;
    }
}
