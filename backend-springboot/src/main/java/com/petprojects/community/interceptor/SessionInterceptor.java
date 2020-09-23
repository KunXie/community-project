package com.petprojects.community.interceptor;

import com.petprojects.community.entity.User;
import com.petprojects.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    private UserService userService;

    @Autowired
    public SessionInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 先检查session's User Attribute
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            return true; // already logged in
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            response.sendRedirect("/authentication/sign-in");
            return false;
        }
        // 根据token判断用户是否登录
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                User user = userService.findUserByToken(token);
                if (user == null) {
                    response.sendRedirect("/authentication/sign-in");
                    return false;
                }
                session.setAttribute("user", user);
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
