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
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.commons.lang3.StringUtils;

public class LocaleFilter implements Filter {

    private static final String LANG_PARAMETER = "lang";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String lang = request.getParameter(LANG_PARAMETER);
        HttpSession session = req.getSession();
        if (StringUtils.isNotBlank(lang) && session != null) {
            Config.set(session, Config.FMT_LOCALE, new Locale(lang));
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
