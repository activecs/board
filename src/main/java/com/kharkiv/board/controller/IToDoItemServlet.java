package com.kharkiv.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IToDoItemServlet extends AbstractAutowiringServlet {
	
	Logger logger = LoggerFactory.getLogger(IToDoItemServlet.class);
    private static final long serialVersionUID = 3018857587188514839L;
    private static final String PAGE_SCHEDULE = "/WEB-INF/pages/schedule.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	logger.debug("debug");
        req.setAttribute("name", req.getParameter("name"));
        req.getRequestDispatcher(PAGE_SCHEDULE).forward(req, resp);
    }
}