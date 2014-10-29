package com.kharkiv.board.service;

import static com.kharkiv.board.util.Constants.CACHE_NAME;
import static com.kharkiv.board.util.Constants.SCHEDULE_CACHE_CONDITION;
import static com.kharkiv.board.util.Constants.SCHEDULE_CACHE_KEY;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kharkiv.board.dao.ScheduleDao;
import com.kharkiv.board.dto.schedule.Schedule;

@Service("scheduleService")
@Transactional
@CacheConfig(cacheNames = CACHE_NAME)
public class ScheduleServiceImpl implements ScheduleService {
	
	private static final String ERR_MESSAGE_USER_ID_CANNOT_BE_NULL = "User id cannot be null";
	private static final String ERR_MESSAGE_USERNAME_CANNOT_BE_EMPTY = "Username cannot be empty";
	private static final String ERR_MESSAGE_SCHEDULE_CANNOT_BE_NULL = "Schedule cannot be null";
	private static final String ERR_MESSAGE_SCHEDULE_ID_CANNOT_BE_NULL = "Schedule id cannot be null";
	
	@Inject
	private ScheduleDao scheduleDao;
	@Inject
	private UserService userService;
	
	@Override
	@Transactional(readOnly = true)
	@Cacheable(key = SCHEDULE_CACHE_KEY)
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
	public List<Schedule> getSchedulesByUsername(String username) {
		if(isEmpty(username))
			throw new IllegalArgumentException(ERR_MESSAGE_USERNAME_CANNOT_BE_EMPTY );
		return scheduleDao.getSchedulesByUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	public Schedule getScheduleById(Integer scheduleId) {
		if(scheduleId == null)
			throw new IllegalArgumentException(ERR_MESSAGE_SCHEDULE_ID_CANNOT_BE_NULL);
		return scheduleDao.getScheduleById(scheduleId);
	}

	@Override
	@CacheEvict(key = SCHEDULE_CACHE_KEY, condition = SCHEDULE_CACHE_CONDITION )
	public void deleteSchedule(Schedule schedule) {
		if(schedule == null)
			throw new IllegalArgumentException(ERR_MESSAGE_SCHEDULE_CANNOT_BE_NULL);
		scheduleDao.deleteSchedule(schedule);
	}

	@Override
	@CacheEvict(key = SCHEDULE_CACHE_KEY, condition = SCHEDULE_CACHE_CONDITION)
	public void deleteScheduleById(Integer scheduleId) {
		if(scheduleId == null)
			throw new IllegalArgumentException(ERR_MESSAGE_SCHEDULE_ID_CANNOT_BE_NULL);
		scheduleDao.deleteScheduleById(scheduleId);
	}

	@Override
	@CacheEvict(key = SCHEDULE_CACHE_KEY, condition = SCHEDULE_CACHE_CONDITION)
	public Schedule addSchedule(Schedule schedule) {
		if(schedule == null)
			throw new IllegalArgumentException(ERR_MESSAGE_SCHEDULE_CANNOT_BE_NULL);
		schedule.setCreated(Calendar.getInstance());
		//schedule.setUser(userService.getCurrentUser());
		return scheduleDao.addSchedule(schedule);
	}

	@Override
	@CacheEvict(key = SCHEDULE_CACHE_KEY, condition = SCHEDULE_CACHE_CONDITION)
	public Schedule updateSchedule(Schedule schedule) {
		if(schedule == null)
			throw new IllegalArgumentException(ERR_MESSAGE_SCHEDULE_CANNOT_BE_NULL);
		return scheduleDao.updateSchedule(schedule);
	}
}