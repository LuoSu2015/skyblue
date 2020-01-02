package com.cskaoyan.controller;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.systemManagement.Roles;
import com.cskaoyan.bean.systemManagement.SystemPermissions;
import com.cskaoyan.service.systemmanagement.RoleService;
import com.cskaoyan.service.systemmanagement.StorageService;

import com.cskaoyan.service.systemmanagement.AdminService;
import com.cskaoyan.util.uploadPic.AliyunOssUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
public class SystemManagementController {


    @Autowired
    RoleService roleService;

    /**
     * 权限统计
     */
    @RequiresPermissions(value = {"admin:role:options"},logical = Logical.OR)
    @RequestMapping("admin/role/options")
    public BaseRespVo countRole(){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        List<Roles> roles = roleService.queryListRoles();
        baseRespVo.setData(roles);
        return baseRespVo;

    }

    @Autowired
    AdminService adminService;

    /**
     * 查找管理员
     * @param page
     * @param limit
     * @param username
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions(value = {"admin:admin:list"},logical = Logical.OR)
    @RequestMapping("admin/admin/list")
    public BaseRespVo adminlist(int page,int limit,String username,String sort,String order){
        BaseRespVo baseRespVo = new BaseRespVo();


        List<Admin> adminList= adminService.queryAdmins(page,limit,username,sort,order);
        HashMap<String, Object> map = new HashMap<>();
        map.put("items",adminList);
        map.put("total",adminList.size());
        baseRespVo.setData(map);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");

        return baseRespVo;
    }


    /**
     * 添加管理员
     * @param
     * @return
     */
    @RequiresPermissions(value = {"admin:admin:create"},logical = Logical.OR)
    @RequestMapping("admin/admin/create")
    //map接收数据
    public BaseRespVo createAdmin(@RequestBody Admin admin){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        Date addTime = new Date();
        Date updateTime = addTime;
        admin.setAddTime(addTime);
        admin.setUpdateTime(updateTime);
        admin.setDeleted(false);
        int i = adminService.insertAdmin(admin);
        Integer id = admin.getId();
        Admin admin2 = adminService.selectAdmin(id);
        baseRespVo.setData(admin2);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 删除管理员
     * @param map
     * @return
     */
    @RequiresPermissions(value = {"admin:admin:delete"},logical = Logical.OR)
    @RequestMapping("admin/admin/delete")
    public BaseRespVo deleteAdmin(@RequestBody Map map){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        int id = (int) map.get("id");
        int i = adminService.deleteAdmin(id);
        return baseRespVo;
    }

    /**
     * 修改管理员
     * @param
     * @return
     */
    @RequiresPermissions(value = {"admin:admin:update"},logical = Logical.OR)
    @RequestMapping("admin/admin/update")
    public BaseRespVo updateAdmin(@RequestBody Admin admin){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        /*Admin admin = new Admin();
        int id = (int) map.get("id");
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        String avatar = (String) map.get("avatar");
        String roleId = (String) map.get("roleIds");
        String roleIds = roleId.toString();

        Date updateTime = new Date();
        admin.setId(id);
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setAvatar(avatar);
        admin.setRoleIds(roleIds);
        admin.setUpdateTime(updateTime);*/
        int i = adminService.updateAdmin(admin);
        baseRespVo.setData(admin);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }



    @Autowired
    StorageService storageService;
    @RequiresPermissions(value = {"admin:role:list"},logical = Logical.OR)
    @RequestMapping("admin/role/list")
    public Map getRoles(int page,int limit, String sort, String order,String name){
        Map map1 = roleService.queryRoles(page, limit, sort, order,name);
        Map map = new HashMap();
        map.put("errno",0);
        map.put("data",map1);
        map.put("errmsg","成功");
        return map;
    }


    /**
     * 创建角色
     * @param role
     * @return
     */
    @RequiresPermissions(value = {"admin:role:create"},logical = Logical.OR)
    @RequestMapping("admin/role/create")
    public BaseRespVo createRole(@RequestBody Role role){
        Role role1 = roleService.createRole(role);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setData(role1);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }




    /**
     * 修改角色
     * @param role
     * @return
     */
    @RequiresPermissions(value = {"admin:role:update"})
    @RequestMapping("admin/role/update")
    public BaseRespVo updateRole(@RequestBody Role role){
        int i = roleService.updateRole(role);
        BaseRespVo baseRespVo = new BaseRespVo();
        if(i == 1){
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("成功");
        }else {
            baseRespVo.setErrno(10001);
            baseRespVo.setErrmsg("插入失败!");
        }
        return baseRespVo;
    }




    /**
     * 删除角色
     * @param role
     * @return
     */
    @RequiresPermissions(value = {"admin:role:delete"},logical = Logical.OR)
    @RequestMapping("admin/role/delete")
    public BaseRespVo deleteRole(@RequestBody Role role){
        int i = roleService.deleteRole(role);
        BaseRespVo baseRespVo = new BaseRespVo();
        if(i == 1){
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("成功");
        }else {
            baseRespVo.setErrno(10001);
            baseRespVo.setErrmsg("删除失败!");
        }
        return baseRespVo;
    }




    /**
     * 授权页面
     */
    @RequiresPermissions(value = {"admin:storage:list"})
    @RequestMapping(value = "admin/role/permissions", method = RequestMethod.GET)
    public BaseRespVo getPermissions(Integer roleId){
        Map map = roleService.getPermissions(roleId);
        BaseRespVo baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

    /**
     * 授权页面
     */
    @RequestMapping(value = "admin/role/permissions", method = RequestMethod.POST)
    public BaseRespVo getPermissions(@RequestBody Map map){
        Integer roleId = (Integer) map.get("roleId");
        List<String> permissions = (List<String>) map.get("permissions");
        roleService.setPermissions(roleId,permissions);
        BaseRespVo baseRespVo = new BaseRespVo<>();

        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");

        return baseRespVo;
    }

    /**
     * 对象存储
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param key
     * @param name
     * @return
     */

    @RequestMapping("admin/storage/list")
    public BaseRespVo getStorages(int page, int limit, String sort, String order,String key,String name){

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        HashMap<String,Object> map = new HashMap();
        PageHelper.startPage(page,limit);
        List<Storage> items = storageService.queryStorages(page,limit,sort,order,key,name);
        PageInfo pageInfo = new PageInfo(items);
        long total = pageInfo.getTotal();
        map.put("total",total);
        map.put("items",items);
        baseRespVo.setData(map);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }




    /**
     * 修改对象
     * @param storage
     * @return
     */

    @RequiresPermissions(value = {"admin:storage:update"},logical = Logical.OR)
    @RequestMapping("admin/storage/update")
    public BaseRespVo updateStorage(@RequestBody Storage storage){
        int i = storageService.changeStorageById(storage);
        BaseRespVo baseRespVo = new BaseRespVo();
        if(i == 1){
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("成功");
        }else {
            baseRespVo.setErrno(10001);
            baseRespVo.setErrmsg("插入失败!");
        }
        return baseRespVo;
    }




    /**
     * 删除对象
     * @param storage
     * @return
     */

    @RequiresPermissions(value = {"admin:storage:delete"},logical = Logical.OR)
    @RequestMapping("admin/storage/delete")
    public BaseRespVo deleteStorage(@RequestBody Storage storage){
        int i = storageService.deleteStorage(storage);
        BaseRespVo baseRespVo = new BaseRespVo();
        if(i == 1){
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("成功");
        }else {
            baseRespVo.setErrno(10001);
            baseRespVo.setErrmsg("删除失败!");
        }
        return baseRespVo;
    }


}
