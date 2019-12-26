package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminController {
    @RequestMapping("admin/auth/login")
    public BaseRespVo login(@RequestBody Map map){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData("abc");
        return baseRespVo;
    }

    @RequestMapping("admin/auth/info")
    public BaseRespVo info(){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        Map<String,Object> map = new HashMap<>();
        map.put("name","admin123");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80");
        ArrayList<String> permList = new ArrayList<>();
        permList.add("*");
        ArrayList<String> roleList = new ArrayList<>();
        roleList.add("超级管理员");
        map.put("perms",permList);
        map.put("roles",roleList);
        baseRespVo.setData(map);
        return baseRespVo;
    }

}
