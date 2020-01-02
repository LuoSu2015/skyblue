package com.cskaoyan.exception;

import com.cskaoyan.bean.BaseRespVo;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


import java.sql.SQLException;
@ControllerAdvice
public class ExceptionControllerAdvise {

  /*  *//* 授权异常 *//*
    @ExceptionHandler({AuthorizationException.class})
    @ResponseBody
    public BaseRespVo handlerAuthorException(Exception e){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(506);
        baseRespVo.setErrmsg("您没有该权限");
        return baseRespVo;
    }
    //认证失败
    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public BaseRespVo handlerAuthenException(Exception e){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(501);
        baseRespVo.setErrmsg("未登录或登录失败");
        return baseRespVo;
    }
    //空指针异常
    @ExceptionHandler({NullPointerException.class})
    @ResponseBody
    public BaseRespVo nullPointException(Exception e){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(507);
        baseRespVo.setErrmsg("您查询的内容为空");
        return baseRespVo;
    }
    //数据库异常
    @ExceptionHandler({SQLException.class})
    @ResponseBody
    public BaseRespVo sqlException(Exception e){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(508);
        baseRespVo.setErrmsg("数据库繁忙,稍后再试");
        return baseRespVo;
    }*/
}
