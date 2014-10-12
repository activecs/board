package com.kharkiv.board.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.service.ScheduleService;

@Controller
@RequestMapping("/schedule")
public class SchedulePageController {

	private static final String ATTRIBUTE_SCHEDULES = "schedules";
	private static final String PAGE_SCHEDULE = "schedule";

	@Inject
	private ScheduleService service;

	@RequestMapping(method = RequestMethod.GET)
	public String getPage(Model model) {
		List<Schedule> schedules = service.getAllSchedules();
		model.addAttribute(ATTRIBUTE_SCHEDULES, schedules);
		return PAGE_SCHEDULE;
	}
}