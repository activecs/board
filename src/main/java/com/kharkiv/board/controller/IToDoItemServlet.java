package com.kharkiv.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IToDoItemServlet extends AbstractAutowiringServlet {

    private static final long serialVersionUID = 3018857587188514839L;
    private static final String PAGE_SCHEDULE = "/WEB-INF/pages/schedule.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("name", req.getParameter("name"));
        req.getRequestDispatcher(PAGE_SCHEDULE).forward(req, resp);
    }
}