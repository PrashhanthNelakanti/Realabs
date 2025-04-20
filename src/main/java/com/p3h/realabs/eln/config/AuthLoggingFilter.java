package com.p3h.realabs.eln.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
public class AuthLoggingFilter extends GenericFilterBean {
    private static final Logger logger = LoggerFactory.getLogger(AuthLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.info("Authentication: name={}, authenticated={}, authorities={}",
                auth.getName(), auth.isAuthenticated(), auth.getAuthorities());
        }
        chain.doFilter(request, response);
    }
}
