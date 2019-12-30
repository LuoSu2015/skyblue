package com.cskaoyan.shiro.config;

import com.cskaoyan.shiro.customRealm.CustomRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager webSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        /*拦截器会把所有的页面请求拦截住，认证失败后的重定向Url*/
        shiroFilterFactoryBean.setLoginUrl("/admin/auth/login");
        shiroFilterFactoryBean.setSecurityManager(webSecurityManager);
        /*对请求的过滤*/
        /*一定要用Linked，因为它是有序的*/
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("/admin/auth/login", "anon");
        linkedHashMap.put("/admin/auth/info", "anon");
        linkedHashMap.put("/*", "anon");
        /* 当你分配了perm1的权限时才能访问need/perm这请求
        filterMap.put("/need/perm","perms[perm1]");
        优选的方式是声明式
        filterMap.put("/auth/logout","logout");*/
        linkedHashMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(linkedHashMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager webSecurityManager(CustomRealm customRealm, DefaultWebSessionManager webSessionManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(customRealm);
        defaultWebSecurityManager.setSessionManager(webSessionManager);
        return defaultWebSecurityManager;
    }

    @Bean
    public DefaultWebSessionManager webSessionManager() {
        CustomSessionManager customSessionManager = new CustomSessionManager();
        return customSessionManager;
    }


    /*声明式鉴权，注解需要的组件*/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager webSecurityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(webSecurityManager);
        return advisor;
    }

    /*
    * servlet异常处理有两种方式，一种是在这里用bean的方式注入，另外一种是通过@ControllerAdvice注入
    * 使用映射处理异常：key为异常全类名 value为异常处理的请求
    *
    * */
   /* @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        *//* 使用的是映射处理手段 *//*
        mappings.put("org.apache.shiro.authz.AuthorizationException","/noperm");
        simpleMappingExceptionResolver.setExceptionMappings(mappings);
        return simpleMappingExceptionResolver;
    }*/

}
