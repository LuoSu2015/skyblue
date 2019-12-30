package com.cskaoyan.service;

import java.util.List;

public interface AuthenService {
    List<String> queryRoleNameByRoleIds(String[] roleId);
    List<String> queryPermissionByRoleIds(String[] roleId);
}
