package com.hu.video.filter;

import com.hu.video.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.filters.ExpiresFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

//@WebFilter(filterName = "loginFilter")
@Slf4j
public class LoginFilter implements Filter {
    public static  String[] urls = {"/pages/"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();

        log.info("url:"+uri);
//        for (String url : urls) {
        log.info(String.valueOf(uri.contains(urls[0])));
        if (uri.contains(urls[0]))
        {

            filterChain.doFilter(request,response);
            return;
//            }
        }
        User user = (User)request.getSession().getAttribute("user");
        if (user!=null)
            filterChain.doFilter(request,response);
//        else response.getWriter().write("error");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
