package com.cskaoyan.mapper;

import com.cskaoyan.bean.Role;
import com.cskaoyan.bean.RoleExample;
import java.util.List;

import com.cskaoyan.bean.systemManagement.Roles;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {

    List<Roles> selectRoles();

    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}
