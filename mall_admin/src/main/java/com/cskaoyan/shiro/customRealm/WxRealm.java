package com.cskaoyan.shiro.customRealm;


import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.AdminMapper;
import com.cskaoyan.mapper.PermissionMapper;
import com.cskaoyan.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WxRealm extends AuthorizingRealm {


    @Autowired
    UserMapper userMapper;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        /* 从数据库获得密码*/
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> userList = userMapper.selectByExample(userExample);
        User user = userList.size() >= 1 ? userList.get(0) : null;
        String credential = user.getPassword();
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, credential, this.getName());
        return simpleAuthenticationInfo;
    }

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        /*Admin admin = (Admin) principalCollection.getPrimaryPrincipal();
//        List<String> stringList=adminMapper.selectPermissionByUsername(username);
//        数据库里面的role_id是一个数组，需要先把数组查出来，遍历找permission
        PermissionExample permissionExample = new PermissionExample();
        PermissionExample.Criteria criteria = permissionExample.createCriteria();
        List<String> permissionList = new ArrayList<>();
        String[] roleIds = admin.getRoleIds();
        for (String roleId : roleIds) {
            criteria.andRoleIdEqualTo(Integer.parseInt(roleId));
            List<Permission> permissions = permissionMapper.selectByExample(permissionExample);
            for (Permission p : permissions) {
                String permission = p.getPermission();
                permissionList.add(permission);
            }
        }*/

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(null);
        return authorizationInfo;
    }


}
