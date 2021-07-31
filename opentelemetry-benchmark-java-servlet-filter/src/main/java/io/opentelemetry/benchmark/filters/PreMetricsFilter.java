package io.opentelemetry.benchmark.filters;

import javax.servlet.*;
import java.io.IOException;

public class PreMetricsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final Long start = System.currentTimeMillis();
        servletRequest.setAttribute("startTime", start);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
        
    }
}
