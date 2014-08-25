package com.kharkiv.board.service;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kharkiv.board.dao.ScheduleDao;
import com.kharkiv.board.dto.schedule.Schedule;

@Service("scheduleService")
@Transactional
public class ScheduleServiceImpl implements ScheduleService {
	
	private static final String ERR_MESSAGE_USER_ID_CANNOT_BE_NULL = "User id cannot be null";
	private static final String ERR_MESSAGE_USER_LOGIN_CANNOT_BE_EMPTY = "User login cannot be empty";
	private static final String ERR_MESSAGE_SCHEDULE_CANNOT_BE_NULL = "Schedule cannot be null";
	private static final String ERR_MESSAGE_SCHEDULE_ID_CANNOT_BE_NULL = "Schedule id cannot be null";
	
	@Inject
	private ScheduleDao scheduleDao; 
	
	@Override
	@Transactional(readOnly = true)
	public List<Schedule> getAllSchedules() {
		return scheduleDao.getAllSchedules();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Schedule> getSchedulesByUserId(Integer userId) {
		if(userId == null)
			throw new IllegalArgumentException(ERR_MESSAGE_USER_ID_CANNOT_BE_NULL);
		return scheduleDao.getSchedulesByUserId(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Schedule> getSchedulesByUserLogin(String userLogin) {
		if(isEmpty(userLogin))
			throw new IllegalArgumentException(ERR_MESSAGE_USER_LOGIN_CANNOT_BE_EMPTY );
		return scheduleDao.getSchedulesByUserLogin(userLogin);
	}

	@Override
	@Transactional(readOnly = true)
	public Schedule getScheduleById(Integer scheduleId) {
		if(scheduleId == null)
			throw new IllegalArgumentException(ERR_MESSAGE_SCHEDULE_ID_CANNOT_BE_NULL);
		return scheduleDao.getScheduleById(scheduleId);
	}

	@Override
	public void deleteSchedule(Schedule schedule) {
		if(schedule == null)
			throw new IllegalArgumentException(ERR_MESSAGE_SCHEDULE_CANNOT_BE_NULL);
		scheduleDao.deleteSchedule(schedule);
	}

	@Override
	public void deleteScheduleById(Integer scheduleId) {
		if(scheduleId == null)
			throw new IllegalArgumentException(ERR_MESSAGE_SCHEDULE_ID_CANNOT_BE_NULL);
		scheduleDao.deleteScheduleById(scheduleId);
	}

	@Override
	public Schedule addSchedule(Schedule schedule) {
		if(schedule == null)
			throw new IllegalArgumentException(ERR_MESSAGE_SCHEDULE_CANNOT_BE_NULL);
		return scheduleDao.addSchedule(schedule);
	}

	@Override
	public Schedule updateSchedule(Schedule schedule) {
		if(schedule == null)
			throw new IllegalArgumentException(ERR_MESSAGE_SCHEDULE_CANNOT_BE_NULL);
		return scheduleDao.updateSchedule(schedule);
	}

}
