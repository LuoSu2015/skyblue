package com.cskaoyan.service.systemManagement;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.bean.Log;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AdminService {

    List<Admin> queryAdmins(int page,int limit,String username,String sort,String order);

    int insertAdmin(Admin admin);

    Admin selectAdmin(Integer id);

    int deleteAdmin(int id);

    int updateAdmin(Admin admin);

    /**
     *  系统管理模块操作日志功能实现
     * @param page 分页的当前页数
     * @param limit 分页的数据条目数
     * @param name 管理员用户名
     * @param sort 排序列名
     * @param order 升序或者降序排列
     * @return 符合条件的全部管理员操作日志信息
     */
    List<Log> loglist(int page, int limit, String name, String sort, String order);

    /**
     * 首页显示功能
     * @return 首页数据统计
     */
    Map dashboard();
}
