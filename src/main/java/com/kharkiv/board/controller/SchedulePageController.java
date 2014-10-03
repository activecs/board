package com.kharkiv.board.controller;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.service.ScheduleService;

@WebServlet("/schedule")
public class SchedulePageController extends AbstractAutowiringServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String ATTRIBUTE_SCHEDULES = "schedules";
	private static final String PAGE_SCHEDULE = "/WEB-INF/pages/schedule.jsp";
	
	@Inject
	private ScheduleService service;
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Schedule> schedules = service.getAllSchedules();
        req.setAttribute(ATTRIBUTE_SCHEDULES, schedules);
    	req.getRequestDispatcher(PAGE_SCHEDULE).forward(req, resp);
    }
}