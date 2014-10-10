package com.kharkiv.board.filter;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

@WebFilter("/*")
public class LocaleFilter implements Filter {

    private static final String LANG_PARAMETER = "lang";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
    ServletException {
    	HttpServletRequest req = (HttpServletRequest) request;
    	String lang = request.getParameter(LANG_PARAMETER);
    	HttpSession session = req.getSession();
    	if (isNotBlank(lang) && session != null) {
    		Config.set(session, Config.FMT_LOCALE, new Locale(lang));
    		return;
    	}
    	chain.doFilter(request, response);
    }
    
    @Override
    public void init(FilterConfig filterConfig) {
    	// Do nothing
    }


    @Override
    public void destroy() {
    	// Do nothing
    }
}