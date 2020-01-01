package com.cskaoyan.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//@ControllerAdvice
public class ExceptionControllerAdvise {

    /* 授权异常 */
//    @ExceptionHandler({AuthorizationException.class})
//    @ResponseBody
    public String handlerAuthorException(Exception e){

        return "noperm";
    }
}
