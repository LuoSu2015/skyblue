package com.cskaoyan.service.systemmanagement;


import com.cskaoyan.bean.Role;
import com.cskaoyan.bean.systemManagement.Roles;

import java.util.List;
import java.util.Map;

public interface RoleService {
    List<Roles> queryListRoles();

    Map queryRoles(int page, int limit, String sort, String order, String name);

    Role createRole(Role role);

    int updateRole(Role role);

    int deleteRole(Role role);
}
