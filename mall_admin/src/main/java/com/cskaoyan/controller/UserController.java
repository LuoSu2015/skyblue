package com.cskaoyan.controller;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.UserMapper;
import com.cskaoyan.service.UserService;
import com.cskaoyan.service.UserServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户模块的实现
 */
@RestController
public class UserController {

    @Autowired
   UserServiceImpl userServiceimpl;

    /**
     * 用户管理模块会员管理功能首页显示和查询具体User
     */
    @RequestMapping("admin/user/list")
    public BaseRespVo listuser(int page,int limit,String username,String mobile,String sort,String order){
        List<User> userList = userServiceimpl.listuser(page, limit,username,mobile,sort,order);
        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        long total = userPageInfo.getTotal();
        Map map = new HashMap();
        map.put("total",total);
        map.put("items",userList);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setData(map);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 用户管理模块收货地址功能首页显示和查询具体User地址接口
     */
    @RequestMapping("admin/address/list")
    public BaseRespVo listaddress(int page,int limit,Integer userId,String name,String sort,String order){
        List<Adress> listaddress = userServiceimpl.listaddress(page, limit, userId, name,sort,order);
        PageInfo<Adress> pageInfo = new PageInfo<>(listaddress);
        long total = pageInfo.getTotal();
        Map map = new HashMap();
        map.put("total",total);
        map.put("items",listaddress);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setData(map);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
    /**
     * 用户管理模块会员收藏功能首页显示和查询具体User收藏商品接口
     */
    @RequestMapping("admin/collect/list")
    public BaseRespVo listcollect(int page,int limit,Integer userId,Integer valueId,String sort,String order){
        List<Collect> listcollect = userServiceimpl.listcollect(page, limit, userId, valueId,sort,order);
        PageInfo<Collect> pageInfo = new PageInfo<>(listcollect);
        long total = pageInfo.getTotal();
        Map map = new HashMap();
        map.put("total",total);
        map.put("items",listcollect);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setData(map);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
    /**
     * 用户管理模块会员足迹功能首页显示及具体User浏览信息查询接口
     */
    @RequestMapping("admin/footprint/list")
    public BaseRespVo listfootprint(int page,int limit,Integer userId,Integer goodsId,String sort,String order){
        List<Footprint> listfootprint = userServiceimpl.listfootprint(page, limit, userId, goodsId, sort, order);
        PageInfo<Footprint> pageInfo = new PageInfo<>(listfootprint);
        long total = pageInfo.getTotal();
        Map map = new HashMap();
        map.put("total",total);
        map.put("items",listfootprint);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setData(map);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
    /**
     * 用户管理模块搜索历史功能首页显示及具体User浏览信息查询接口
     */
    @RequestMapping("admin/history/list")
    public BaseRespVo listhistory(int page,int limit,Integer userId,String keyword,String sort,String order){
        List<SearchHistory> listhistory = userServiceimpl.listhistory(page, limit, userId, keyword, sort, order);
        PageInfo<SearchHistory> pageInfo = new PageInfo<>(listhistory);
        long total = pageInfo.getTotal();
        Map map = new HashMap();
        map.put("total",total);
        map.put("items",listhistory);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setData(map);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
    @RequestMapping("admin/feedback/list")
    /**
     * 用户管理模块意见反馈功能首页显示及具体User反馈信息查询接口
     */
    public BaseRespVo listfeedback(int page,int limit,Integer id,String username,String sort,String order){
        List<Feedback> listfeedback = userServiceimpl.listfeedback(page, limit, id, username, sort, order);
        PageInfo<Feedback> pageInfo = new PageInfo<>(listfeedback);
        long total = pageInfo.getTotal();
        Map map = new HashMap();
        map.put("total",total);
        map.put("items",listfeedback);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setData(map);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
