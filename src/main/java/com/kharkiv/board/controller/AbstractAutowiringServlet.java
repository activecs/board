package com.kharkiv.board.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

public abstract class AbstractAutowiringServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    protected AutowireCapableBeanFactory ctx;

    @Override
    public void init() throws ServletException {
        super.init();
        ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getAutowireCapableBeanFactory();
        ctx.autowireBean(this);
    }

}
