package com.example.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    private String[] excludedPages;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        String excludedPage = filterConfig.getInitParameter("excludedPages");
//        System.out.println(excludedPage);
//        if (excludedPage !=null && excludedPage.length()>0){
//            excludedPages = excludedPage.split(",");
//        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        if (requestURI.contains("userLogin.html") || requestURI.contains("register.html") || requestURI.contains("forgetPwd.html")){
            filterChain.doFilter(servletRequest, servletResponse);
        }

        Object admin = request.getSession().getAttribute("admin");
        if (admin==null){
            response.sendRedirect("/templates/userLogin.html");
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
