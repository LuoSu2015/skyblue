package com.cskaoyan.service.systemManagement;


import com.cskaoyan.bean.Role;
import com.cskaoyan.bean.RoleExample;
import com.cskaoyan.bean.systemManagement.Roles;
import com.cskaoyan.mapper.RoleMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

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
}
