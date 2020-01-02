package com.cskaoyan.service.systemmanagement;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements com.cskaoyan.service.systemmanagement.AdminService {

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

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    GoodsProductMapper goodsProductMapper;
    @Autowired
    OrderMapper orderMapper;

    /**
     * 首页显示功能
     * @return 首页数据统计
     */
    @Override
    public Map dashboard() {
        GoodsExample goodsExample = new GoodsExample();
        UserExample userExample = new UserExample();
        GoodsProductExample goodsProductExample = new GoodsProductExample();
        OrderExample orderExample = new OrderExample();
        Map map = new HashMap();
        long goods = goodsMapper.countByExample(goodsExample);
        long users = userMapper.countByExample(userExample);
        long products = goodsProductMapper.countByExample(goodsProductExample);
        long orders = orderMapper.countByExample(orderExample);
        map.put("goodsTotal",goods);
        map.put("userTotal",users);
        map.put("productTotal",products);
        map.put("orderTotal",orders);
        return map;
    }
}
