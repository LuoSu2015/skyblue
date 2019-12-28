package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Log;
import com.cskaoyan.bean.User;

import com.cskaoyan.service.systemManagement.AdminServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AdminController {
    /**
     * 登录
     * @param map
     * @return
     */
    @RequestMapping("admin/auth/login")
    public BaseRespVo login(@RequestBody Map map){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData("abc");
        return baseRespVo;
    }

    /**
     * 首页的信息
     * @return
     */
    @RequestMapping("admin/auth/info")
    public BaseRespVo info(){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        Map<String,Object> map = new HashMap<>();
        map.put("name","admin123");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        ArrayList<String> permList = new ArrayList<>();
        permList.add("*");
        ArrayList<String> roleList = new ArrayList<>();
        roleList.add("超级管理员");
        map.put("perms",permList);
        map.put("roles",roleList);
        baseRespVo.setData(map);
        return baseRespVo;
    }




    @Autowired
    AdminServiceImpl adminService;
    /**
     * 系统管理模块操作日志功能实现
     */
    @RequestMapping("admin/log/list")
    public BaseRespVo loglist(int page,int limit,String name,String sort,String order){
        List<Log> loglist = adminService.loglist(page, limit, name, sort, order);
        PageInfo<Log> pageInfo = new PageInfo<>(loglist);
        long total = pageInfo.getTotal();
        Map map = new HashMap();
        map.put("total",total);
        map.put("items",loglist);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setData(map);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
