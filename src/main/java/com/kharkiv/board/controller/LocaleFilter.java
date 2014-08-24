package com.kharkiv.board.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.commons.lang3.StringUtils;

public class LocaleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (StringUtils.isNotBlank(request.getParameter("lang")) && req.getSession() != null) {
            Config.set(req.getSession(), Config.FMT_LOCALE, new Locale(request.getParameter("lang")));
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
