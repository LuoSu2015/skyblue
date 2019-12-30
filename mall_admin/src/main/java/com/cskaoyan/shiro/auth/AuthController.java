package com.cskaoyan.shiro.auth;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

@Controller
public class AuthController {


   /* @RequestMapping("admin/auth/login")
    @ResponseBody*/
    public BaseRespVo login(@RequestBody User user) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken authenticationToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            subject.login(authenticationToken);
        } catch (AuthenticationException e) {
            baseRespVo.setErrmsg("用户账号或密码不正确");
            baseRespVo.setErrno(605);
            return baseRespVo;
        }
        Serializable sessionId = subject.getSession().getId();
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        baseRespVo.setData(sessionId);
        return baseRespVo;
    }

   /* @RequestMapping("school/appoint")
    @RequiresPermissions(value = {"appoint", "command"}, logical = Logical.OR)
    public String needPerm() {
        Subject subject = SecurityUtils.getSubject();

        return "permission";
    }*/

}
