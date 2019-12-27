package com.cskaoyan.service;


import com.cskaoyan.bean.Log;
import com.cskaoyan.bean.LogExample;
import com.cskaoyan.mapper.LogMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    LogMapper logMapper;


    /**
     * 系统管理模块操作日志功能实现
     * @param page 分页的当前页数
     * @param limit 分页的数据条目数
     * @param name 管理员用户名
     * @param sort 排序列名
     * @param order 升序或者降序排列
     * @return 符合条件的全部管理员操作日志信息
     */
    @Override
    public List<Log> loglist(int page, int limit, String name, String sort, String order) {
        LogExample logExample = new LogExample();
        PageHelper.startPage(page,limit);
        String orderby = sort + " " + order;
        String s = "%" + name + "%";
        PageHelper.orderBy(orderby);
        if (name != null){
            logExample.createCriteria().andAdminLike(s);
        }
        List<Log> logs = logMapper.selectByExample(logExample);
        return logs;
    }
}
