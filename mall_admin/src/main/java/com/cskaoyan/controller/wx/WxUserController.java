package com.cskaoyan.controller.wx;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Order;
import com.cskaoyan.bean.User;
import com.cskaoyan.bean.wx.OrderStatus;
import com.cskaoyan.bean.wx.WxWrapper;
import com.cskaoyan.service.AuthenService;
import com.cskaoyan.shiro.config.ShiroConfig;
import com.cskaoyan.shiro.token.UserToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WxUserController {

  /*  @Autowired
    ShiroConfig shiroConfig;*/

    @Autowired
    AuthenService authenService;

    @RequestMapping("wx/auth/login")
    public BaseRespVo userLogin(@RequestBody UserToken userToken) {
        userToken.setType("wx");
        WxWrapper wxWrapper = new WxWrapper();
        BaseRespVo baseRespVo = new BaseRespVo();
        Subject subject = SecurityUtils.getSubject();
        Map map = new HashMap();
        try {
            subject.login(userToken);
            Serializable sessionId = subject.getSession().getId();
            User user = authenService.queryUserByName(userToken.getUsername());
            wxWrapper.setNickName(user.getNickname());
            wxWrapper.setAvatarUrl(user.getAvatar());
//            String sessionId = shiroConfig.webSessionManager().getSessionId();
            Date date = new Date(new Date().getTime() + 60 * 60 * 24*10);
            map.put("userInfo", wxWrapper);
            map.put("token",sessionId);
            map.put("tokenExpire",date);
            baseRespVo.setErrmsg("成功");
            baseRespVo.setErrno(0);
            baseRespVo.setData(map);
        } catch (AuthenticationException e) {
            baseRespVo.setErrno(402);
            baseRespVo.setErrmsg("参数不对");
        }
        return baseRespVo;
    }

    /*未完成，*/
    @RequestMapping("wx/user/index")
    public BaseRespVo userIndex(){
        BaseRespVo baseRespVo = new BaseRespVo();
       /* Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Order order=authenService.queryOrderByUserId(user.getId());*/
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setUncomment(0);
        orderStatus.setUnpaid(0);
        orderStatus.setUnrecv(0);
        orderStatus.setUnship(0);

        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        baseRespVo.setData(orderStatus);
        return baseRespVo;
    }

    /*注销*/
    @RequestMapping("wx/auth/logout")
    public BaseRespVo logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
