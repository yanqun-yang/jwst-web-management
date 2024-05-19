package com.yanqun.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

//@WebFilter(urlPatterns = "/*")
public class AbcFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("AbcFilter拦截方法执行，拦截到了请求。。。");
        //放行
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("AbcFilter执行放行后的操作。。。");
    }
}
