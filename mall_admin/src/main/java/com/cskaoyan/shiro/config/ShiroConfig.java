package com.cskaoyan.shiro.config;

import com.cskaoyan.shiro.customRealm.AdminRealm;
import com.cskaoyan.shiro.customRealm.WxRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
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
//        linkedHashMap.put("/wx/auth/login","anon");
        linkedHashMap.put("/admin/auth/login", "anon");


        /* 当你分配了perm1的权限时才能访问need/perm这请求
        filterMap.put("/need/perm","perms[perm1]");
        优选的方式是声明式
        filterMap.put("/auth/logout","logout");*/


        linkedHashMap.put("/wx/**", "anon");
        linkedHashMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(linkedHashMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager webSecurityManager(CustomAuthenticator authenticator, DefaultWebSessionManager webSessionManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setSessionManager(webSessionManager);
        defaultWebSecurityManager.setAuthenticator(authenticator);
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

    /* realm分离器 也可以通过CustomAuthenticator以@Component直接注册在容器里*/
    @Bean
    public CustomAuthenticator authenticator(AdminRealm adminRealm, WxRealm wxRealm) {
        CustomAuthenticator customAuthenticator = new CustomAuthenticator();
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(adminRealm);
        realms.add(wxRealm);
        customAuthenticator.setRealms(realms);
        return customAuthenticator;
    }
}
