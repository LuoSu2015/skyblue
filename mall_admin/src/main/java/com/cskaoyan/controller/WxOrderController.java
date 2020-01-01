package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.User;
import com.cskaoyan.service.OrderService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class WxOrderController {

    @Autowired
    OrderService orderService;

    /**
     * 501 请登录
     */
    @RequestMapping("wx/order/list")
    public BaseRespVo getOrderList(Integer showType, Integer page, Integer size){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer id = user.getId();
        Map data = orderService.queryOrder(showType,page,size,id);

        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
    @RequestMapping("wx/order/detail")
    public BaseRespVo getGoodDetail(){

        return null;
    }
}
