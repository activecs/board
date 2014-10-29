package com.kharkiv.board.dao;

import java.util.List;

import com.kharkiv.board.dto.schedule.Schedule;

public interface ScheduleDao {

	List<Schedule> getAllSchedules();

	List<Schedule> getSchedulesByUserId(Integer userId);

	List<Schedule> getSchedulesByUsername(String username);

	Schedule getScheduleById(Integer id);

	void deleteSchedule(Schedule schedule);

	int deleteScheduleById(Integer id);

	Schedule addSchedule(Schedule schedule);

	Schedule updateSchedule(Schedule schedule);
}
