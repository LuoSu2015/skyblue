package com.cskaoyan.service;

import com.cskaoyan.bean.Order;
import com.cskaoyan.bean.User;

import java.util.List;

public interface AuthenService {
    List<String> queryRoleNameByRoleIds(Integer[] roleId);
    List<String> queryPermissionByRoleIds(Integer[] roleId);

    Boolean isCorrectPassword(String oldPassword);

    Boolean modifyPassword(String newPassword);

    User queryUserByName(String username);

    Order queryOrderByUserId(Integer id);
}
