package com.cskaoyan.service;

import com.cskaoyan.bean.Permission;
import com.cskaoyan.bean.PermissionExample;
import com.cskaoyan.bean.Role;
import com.cskaoyan.bean.RoleExample;
import com.cskaoyan.mapper.PermissionMapper;
import com.cskaoyan.mapper.RoleMapper;
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
}
