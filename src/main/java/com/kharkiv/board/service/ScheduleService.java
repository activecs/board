package com.kharkiv.board.service;

import java.util.List;

import com.kharkiv.board.dto.schedule.Schedule;

public interface ScheduleService {

	List<Schedule> getAllSchedules();

	List<Schedule> getSchedulesByUserId(Integer userId);

	List<Schedule> getSchedulesByUsername(String username);

	Schedule getScheduleById(Integer scheduleId);

	void deleteSchedule(Schedule schedule);

	void deleteScheduleById(Integer scheduleId);

	Schedule addSchedule(Schedule schedule);

	Schedule updateSchedule(Schedule schedule);

}