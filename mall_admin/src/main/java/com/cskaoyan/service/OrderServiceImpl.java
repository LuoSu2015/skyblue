package com.cskaoyan.service;

import com.cskaoyan.bean.Order;
import com.cskaoyan.bean.OrderExample;
import com.cskaoyan.mapper.OrderMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<Order> queryOrder(Integer showType, Integer page, Integer size, Integer id) {
        PageHelper.startPage(page,size);
        if(showType == 0){

            OrderExample example = new OrderExample();
            OrderExample.Criteria criteria = example.createCriteria();

            List<Order> orders = orderMapper.selectByExample(example);
        }else if(showType == 1){

        }else if(showType == 2){

        }else if(showType == 3){

        }else if(showType == 4){

        }
        return null;
    }
}
