package com.kharkiv.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/schedule")
public class SchedulePageController extends AbstractAutowiringServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String PAGE_SCHEDULE = "/WEB-INF/pages/schedule.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PAGE_SCHEDULE).forward(req, resp);
    }
}