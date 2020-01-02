package com.cskaoyan.service.systemmanagement;


import com.cskaoyan.bean.Permission;
import com.cskaoyan.bean.PermissionExample;
import com.cskaoyan.bean.Role;
import com.cskaoyan.bean.RoleExample;
import com.cskaoyan.bean.systemManagement.Roles;

import com.cskaoyan.bean.systemManagement.SystemPermissions;
import com.cskaoyan.mapper.PermissionMapper;
import com.cskaoyan.mapper.RoleMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public List<Roles> queryListRoles() {
        List<Roles> roles = roleMapper.selectRoles();
        return roles;
    }



    @Override
    public Map queryRoles(int page, int limit, String sort, String order, String name) {
        PageHelper.startPage(page,limit);
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        if(name != null){
            criteria.andNameLike("%"+name+"%");
        }
        criteria.andDeletedEqualTo(false);
        roleExample.setOrderByClause(sort+" "+order);
        List<Role> roles = roleMapper.selectByExample(roleExample);
        long l = roleMapper.countByExample(null);
        Map map = new HashMap();
        map.put("total",l);
        map.put("items",roles);
        return map;
    }

    @Override
    public Role createRole(Role role) {
        role.setAddTime(new Date());
        role.setDeleted(false);
        role.setEnabled(true);
        roleMapper.insert(role);
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andNameEqualTo(role.getName());
        List<Role> roles = roleMapper.selectByExample(roleExample);
        return roles.get(0);
    }

    @Override
    public int updateRole(Role role) {
        int i = roleMapper.updateByPrimaryKey(role);
        return i;
    }

    @Override
    public int deleteRole(Role role) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdEqualTo(role.getId());
        role.setDeleted(true);
        int i = roleMapper.updateByExampleSelective(role, roleExample);
        return i;
    }

    @Override
    public Map getPermissions(Integer roleId) {
         List<SystemPermissions> systemPermissionsList = permissionMapper.selectAll();

        /* List<SystemPermissions> systemPermissionForFronts = new ArrayList<>();
         List<SystemPermissions> systemPermissionsList1 = new ArrayList<>();
         List<SystemPermissions> systemPermissionsList2 = new ArrayList<>();
        for (SystemPermissions systemPermissions : systemPermissionsList) {
            systemPermissionsList1.add(systemPermissions);
            systemPermissionsList2.add(systemPermissions);
        }
        for (SystemPermissions systemPermissions : systemPermissionsList) {
            if(systemPermissions.getPid() == 0){
                systemPermissionForFronts.add(systemPermissions);
            }
        }
        for (SystemPermissions systemPermissionForFront : systemPermissionForFronts) {
            for (SystemPermissions systemPermissions : systemPermissionsList) {
                if(systemPermissions.getPid() == systemPermissionForFront.getPrimaryId()){

                }
            }
        }*/


        PermissionExample permissionExample = new PermissionExample();
        PermissionExample.Criteria criteria = permissionExample.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        List<Permission> permissions = permissionMapper.selectByExample(permissionExample);
        List<String> list = new ArrayList<>();
        for (Permission permission : permissions) {
            list.add(permission.getPermission());
        }
        Map map = new HashMap();
        map.put("systemPermissions",systemPermissionsList);
        map.put("assignedPermissions",list);
        return map;
    }

    @Override
    public void setPermissions(Integer roleId, List<String> permissions) {
        PermissionExample example = new PermissionExample();
        PermissionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        List<Permission> permissions1 = permissionMapper.selectByExample(example);
        StringBuilder stringBuilder = new StringBuilder();
        for (Permission permission : permissions1) {
            stringBuilder.append(permission.getPermission());
        }
        for (int i = 0; i < permissions.size(); i++) {
            Permission permission = new Permission();
            permission.setRoleId(roleId);
            permission.setDeleted(false);
            permission.setAddTime(new Date());
            permission.setPermission(permissions.get(i));
            permissionMapper.insertSelective(permission);
        }

    }
}
