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
        String requestURI = servletRequest.getRequestURI();
        if(requestURI.toLowerCase().contains("admin")){
            String adminSessionId = servletRequest.getHeader("X-cskaoyan-mall-Admin-Token");
            if (adminSessionId != null && !"".equals(adminSessionId)) {
                return adminSessionId;
            }
        }
        if(requestURI.toLowerCase().contains("wx")){
//            String wxSessionId = servletRequest.getHeader("X-Litemall-Token");
            String wxSessionId = servletRequest.getHeader("X-cskaoyan-mall-Admin-Token");
            if (wxSessionId != null && !"".equals(wxSessionId)) {
                return wxSessionId;
            }
        }

        return super.getSessionId(request, response);
    }

    /*public String getSessionIdNoArgs() {
        if (sessionId != null && !"".equals(sessionId)) {
            return sessionId;
        }else {
            return null;
        }
    }*/
}
