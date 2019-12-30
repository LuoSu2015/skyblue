package com.cskaoyan.shiro.config;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/*这个是用于前后端分离的时候用，不然容易出错，一直无法获得SessionId*/
public class CustomSessionManager extends DefaultWebSessionManager {

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        /*这个根据服务器里面的信息来判断需要获取什么*/
        String sessionId = servletRequest.getHeader("X-cskaoyan-mall-Admin-Token");
        if(sessionId !=null && !"".equals(sessionId)){
            return sessionId;
        }
        return super.getSessionId(request, response);
    }
}