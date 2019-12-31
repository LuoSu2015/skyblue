package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenServiceImpl implements AuthenService{

    @Autowired
    RoleMapper roleMapper;
    @Override
    public List<String> queryRoleNameByRoleIds(String[] roleIds) {

        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        List<String> roleNameList = new ArrayList<>();
        for (String roleId : roleIds) {
            int i = Integer.parseInt(roleId);
            criteria.andIdEqualTo(i);
            List<Role> roles = roleMapper.selectByExample(roleExample);
            for (Role r : roles) {
                roleNameList.add(r.getName());
            }
        }

        return roleNameList;
    }

    @Autowired
    PermissionMapper permissionMapper;
    @Override
    public List<String> queryPermissionByRoleIds(String[] roleIds) {
        PermissionExample permissionExample = new PermissionExample();
        PermissionExample.Criteria criteria = permissionExample.createCriteria();
        List<String> permissionList = new ArrayList();
        for (String roleId : roleIds) {
            criteria.andRoleIdEqualTo(Integer.parseInt(roleId));
            List<Permission> pemissions = permissionMapper.selectByExample(permissionExample);
            for (Permission p : pemissions) {
                permissionList.add(p.getPermission());
            }
        }
        return permissionList;
    }

    @Autowired
    AdminMapper adminMapper;

    @Override
    public Boolean isCorrectPassword(String oldPassword) {
        Subject subject = SecurityUtils.getSubject();
        Admin admin = (Admin) subject.getPrincipal();
        String password = admin.getPassword();
        if(password.equals(oldPassword)){
            return true;
        }
        return false;
    }

    @Override
    public Boolean modifyPassword(String newPassword) {
        Subject subject = SecurityUtils.getSubject();
        Admin admin = (Admin) subject.getPrincipal();
        admin.setPassword(newPassword);
        int i = adminMapper.updateByPrimaryKeySelective(admin);
        return true;
    }

    @Autowired
    UserMapper userMapper;
    @Override
    public User queryUserByName(String username) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        User user = userMapper.selectByExample(userExample).get(0);
        return user;
    }

    /*有问题还没有完成，因为这是联合查询*/
    @Autowired
    OrderMapper orderMapper;
    @Override
    public Order queryOrderByUserId(Integer userId) {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        Order order = orderMapper.selectByExample(orderExample).get(0);
        return order;
    }
}
