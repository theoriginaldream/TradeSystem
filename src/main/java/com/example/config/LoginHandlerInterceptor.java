package com.example.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session!=null){
            Object admin = session.getAttribute("admin");
            if (admin==null){
                session.setAttribute("msg","权限不足，请登录");
                request.getRequestDispatcher("/templates/userLogin.html").forward(request,response);
                return false;
            }else {
                return true;
            }
        }
        response.sendRedirect("/templates/userLogin.html");
        return false;
    }


}
