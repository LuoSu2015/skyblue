package com.cskaoyan.controller;


import com.cskaoyan.bean.Admin;
import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Log;
import com.cskaoyan.service.AuthenService;
import com.cskaoyan.service.systemmanagement.AdminServiceImpl;
import com.cskaoyan.shiro.token.UserToken;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class AdminController {

    @Autowired
    AuthenService authenService;


    @RequestMapping("admin/auth/login")
    public BaseRespVo login(@RequestBody UserToken userToken) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Subject subject = SecurityUtils.getSubject();
        try {
            userToken.setType("admin");
            subject.login(userToken);
        } catch (AuthenticationException e) {
            baseRespVo.setErrmsg("用户账号或密码不正确");
            baseRespVo.setErrno(605);
            return baseRespVo;
        }
        Serializable sessionId = subject.getSession().getId();
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        baseRespVo.setData(sessionId);
        return baseRespVo;
    }


    @RequestMapping("admin/auth/info")
    public BaseRespVo info(String token) {
        Subject subject = SecurityUtils.getSubject();
        Admin admin = (Admin) subject.getPrincipal();
        String username = admin.getUsername();
        String avatar = admin.getAvatar();
        String[] roleIds = admin.getRoleIds();

        List<String> roleNameList = authenService.queryRoleNameByRoleIds(roleIds);
        List<String> permissionList = authenService.queryPermissionByRoleIds(roleIds);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);

        Map<String, Object> map = new HashMap<>();
        map.put("name", username);
        map.put("avatar", avatar);
        map.put("perms", permissionList);
        map.put("roles", roleNameList);
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /*修改密码*/
    @RequestMapping("admin/profile/password")
    public BaseRespVo modifyPassword(@RequestBody Map map) {
        String newPassword = (String) map.get("newPassword");
        String newPassword2 = (String) map.get("newPassword2");
        String oldPassword = (String) map.get("oldPassword");
        BaseRespVo baseRespVo = new BaseRespVo<>();
        Boolean flag = authenService.isCorrectPassword(oldPassword);
        if(flag){
            if(newPassword!=null&& !"".equals(newPassword)&&newPassword2!=null&&!"".equals(newPassword2))
                if(newPassword.equals(newPassword2)){
                    authenService.modifyPassword(newPassword);
                    baseRespVo.setErrmsg("成功");
                    baseRespVo.setErrno(0);
                    return baseRespVo;
                }
        }
        baseRespVo.setErrno(605);
        baseRespVo.setErrmsg("账号密码不对");
        return baseRespVo;
    }


    @Autowired
    AdminServiceImpl adminService;

    /**
     * 系统管理模块操作日志功能实现
     */
    @RequestMapping("admin/log/list")
    public BaseRespVo loglist(int page, int limit, String name, String sort, String order) {
        List<Log> loglist = adminService.loglist(page, limit, name, sort, order);
        PageInfo<Log> pageInfo = new PageInfo<>(loglist);
        long total = pageInfo.getTotal();
        Map map = new HashMap();
        map.put("total", total);
        map.put("items", loglist);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setData(map);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /*注销*/
    @RequestMapping("admin/auth/logout")
    public BaseRespVo logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("admin/unAuth")
    public BaseRespVo unauth(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(501);
        baseRespVo.setErrmsg("未登录!");
        return baseRespVo;
    }


}
