package com.cskaoyan.service.systemManagement;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.bean.AdminExample;
import com.cskaoyan.bean.Log;
import com.cskaoyan.bean.LogExample;
import com.cskaoyan.mapper.AdminMapper;
import com.cskaoyan.mapper.LogMapper;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    /**
     * 查找管理员
     * @param page
     * @param limit
     * @param username
     * @param sort
     * @param order
     * @return
     */
    @Override
    public List<Admin> queryAdmins(int page,int limit ,String username,String sort,String order) {
        AdminExample adminExample = new AdminExample();
        PageHelper.startPage(page, limit);
        String orderby = sort + " " + order;
        PageHelper.orderBy(orderby);
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(username != null){
            username = "%" + username + "%";
            criteria.andUsernameLike(username);

        }

        List<Admin> admins = adminMapper.selectByExample(adminExample);
        return admins;
    }

    /**
     * 新增管理员
     * @return
     */
    @Override
    public int insertAdmin(Admin admin) {

        int insert = adminMapper.insert(admin);

        return insert;

    }

    /**
     * 添加管理员
     * 根据id来查找一个管理员的信息
     * @param id
     * @return
     */
    @Override
    public Admin selectAdmin(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    /**
     * 逻辑删除管理员
     * @param id
     * @return
     */
    @Override
    public int deleteAdmin(int id) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andIdEqualTo(id);

        Admin admin = new Admin();
        admin.setDeleted(true);
        int i = adminMapper.updateByExampleSelective(admin, adminExample);
        return  i;
    }

    /**
     * 更新管理员
     * @param admin
     * @return
     */
    @Override
    public int updateAdmin(Admin admin) {
        int update = adminMapper.updateByPrimaryKey(admin);
        return update;
    }


    @Autowired
    LogMapper logMapper;

    LogExample logExample = new LogExample();
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
