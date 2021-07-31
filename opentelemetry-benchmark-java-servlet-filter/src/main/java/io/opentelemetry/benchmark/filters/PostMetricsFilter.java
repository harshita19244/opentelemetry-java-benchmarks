package io.opentelemetry.benchmark.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class PostMetricsFilter implements Filter {

    final static Logger logger = Logger.getLogger(PostMetricsFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,FilterChain filterChain) throws IOException, ServletException {

        final Long finish = System.currentTimeMillis();
        final long start = (long) servletRequest.getAttribute("startTime");
        logger.info("Start time: " + start + " Finish time: " + finish + " " +
                " Duration: " + (finish - start));
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
