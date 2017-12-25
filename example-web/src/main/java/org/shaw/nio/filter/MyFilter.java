package org.shaw.nio.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * Created by joy on 17-1-30.
 */

@WebFilter(filterName = "filterName",//过滤器名称
        value = {"url0", "url2"},//匹配URL
        asyncSupported = true,  //是否支持异步
        dispatcherTypes = {DispatcherType.ASYNC, DispatcherType.ERROR},//ASYNC是否异步，FORWARD 通过RequestDispatcher的forward访问,INCLUDE 通过RequestDispatcher的include访问
        initParams = {@WebInitParam(name = "key", value = "value"), @WebInitParam(name = "key2", value = "value2")} //初始化参数
)
public class MyFilter implements Filter {
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init filter!");
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("start doFilter!");
        String value = filterConfig.getInitParameter("key");

        filterChain.doFilter(servletRequest, servletResponse);

        System.out.println("end doFilter!");
    }

    @Override
    public void destroy() {
        System.out.println("destroy filter!");
    }
}
