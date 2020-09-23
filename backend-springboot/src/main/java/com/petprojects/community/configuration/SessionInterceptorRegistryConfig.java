package com.petprojects.community.configuration;

import com.petprojects.community.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SessionInterceptorRegistryConfig implements WebMvcConfigurer {

    private final SessionInterceptor sessionInterceptor;

    @Autowired
    public SessionInterceptorRegistryConfig(SessionInterceptor sessionInterceptor) {
        this.sessionInterceptor = sessionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // '/' 是不需要登录的
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/profile/**");
    }
}
