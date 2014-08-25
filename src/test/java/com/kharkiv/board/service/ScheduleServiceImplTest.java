package com.kharkiv.board.service;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.kharkiv.board.dao.ScheduleDao;
import com.kharkiv.board.dto.schedule.Schedule;

public class ScheduleServiceImplTest {
	
	private static final Integer USER_ID = 15;
	private static final Integer SCHEDULE_ID = 16;
	private static final String USER_LOGIN = "userLogin";
	
	@InjectMocks
	private ScheduleService service = new ScheduleServiceImpl();
	@Mock
	private ScheduleDao mockScheduleDao;
	@Mock
	private Schedule mockSchedule;
	
	private List<Schedule> schedules;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
		schedules = newArrayList(mockSchedule);
		initMockBehaviour();
	}

	private void initMockBehaviour() {
		when(mockScheduleDao.getAllSchedules()).thenReturn(schedules);
		when(mockScheduleDao.getSchedulesByUserId(USER_ID)).thenReturn(schedules);
		when(mockScheduleDao.getSchedulesByUserLogin(USER_LOGIN)).thenReturn(schedules);
		when(mockScheduleDao.getScheduleById(SCHEDULE_ID)).thenReturn(mockSchedule);
		when(mockScheduleDao.addSchedule(mockSchedule)).thenReturn(mockSchedule);
		when(mockScheduleDao.updateSchedule(mockSchedule)).thenReturn(mockSchedule);
	}

	@Test
	public void shouldCallGetAllSchedulesOnDao_whenCallGetAllSchedules() {
		service.getAllSchedules();
		verify(mockScheduleDao).getAllSchedules();
	}
	
	@Test
	public void shouldReturnCommentsReturnedByDao_whenCallGetAllCommentsForUser() {
		List<Schedule> actualSchedules = service.getAllSchedules();
		assertThat(actualSchedules).isEqualTo(schedules);
	}
	
	@Test
	public void shouldCallGetSchedulesByUserIdOnDaoWithGivenUserId_whenCallGetSchedulesByUserId() {
		service.getSchedulesByUserId(USER_ID);
		verify(mockScheduleDao).getSchedulesByUserId(USER_ID);
	}
	
	@Test
	public void shouldReturnSchedulesReturnedByDao_whenCallGetSchedulesByUserId() {
		List<Schedule> actualSchedules = service.getSchedulesByUserId(USER_ID);
		assertThat(actualSchedules).isEqualTo(schedules);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserIdIsNullAndCallGetSchedulesByUserId() {
		service.getSchedulesByUserId(null);
	}
	
	@Test
	public void shouldCallGetSchedulesByUserLoginOnDaoWithGivenUserLogin_whenCallGetSchedulesByUserLogin() {
		service.getSchedulesByUserLogin(USER_LOGIN);
		verify(mockScheduleDao).getSchedulesByUserLogin(USER_LOGIN);
	}
	
	@Test
	public void shouldReturnSchedulesReturnedByDao_whenCallGetSchedulesByUserLogin() {
		List<Schedule> actualSchedules = service.getSchedulesByUserLogin(USER_LOGIN);
		assertThat(actualSchedules).isEqualTo(schedules);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserLoginIsNullAndCallGetSchedulesByUserLogin() {
		service.getSchedulesByUserLogin(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserLoginIsEmptyAndCallGetSchedulesByUserLogin() {
		service.getSchedulesByUserLogin(EMPTY);
	}
	
	@Test
	public void shouldCallGetSchedulesByIdOnDaoWithGivenScheduleId_whenCallGetScheduleById() {
		service.getScheduleById(SCHEDULE_ID);
		verify(mockScheduleDao).getScheduleById(SCHEDULE_ID);
	}
	
	@Test
	public void shouldReturnSchedulesReturnedByDao_whenCallGetScheduleById() {
		Schedule actualSchedule = service.getScheduleById(SCHEDULE_ID);
		assertThat(actualSchedule).isEqualTo(mockSchedule);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenScheduleIdIsNullAndCallGetScheduleById() {
		service.getScheduleById(null);
	}
	
	@Test
	public void shouldCallDeleteScheduleOnDaoWithGivenSchedule_whenCallDeleteSchedule() {
		service.deleteSchedule(mockSchedule);
		verify(mockScheduleDao).deleteSchedule(mockSchedule);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserIsNullAndCallDeleteSchedule() {
		service.deleteSchedule(null);
	}
	
	@Test
	public void shouldCallDeleteScheduleByIdOnDaoWithGivenScheduleId_whenCallDeleteScheduleById() {
		service.deleteScheduleById(SCHEDULE_ID);
		verify(mockScheduleDao).deleteScheduleById(SCHEDULE_ID);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserIdIsNullAndCallDeleteScheduleById() {
		service.deleteScheduleById(null);
	}
	
	@Test
	public void shouldCallAddScheduleWithGivenSchedule_whenCallAddSchedule() {
		service.addSchedule(mockSchedule);
		verify(mockScheduleDao).addSchedule(mockSchedule);
	}
	
	@Test
	public void shouldReturnScheduleReturnedByDao_whenCallAddSchedule() {
		Schedule actualSchedule = service.addSchedule(mockSchedule);
		assertThat(actualSchedule).isEqualTo(mockSchedule);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenScheduleIsNullAndCallAddSchedule() {
		service.addSchedule(null);
	}
	
	@Test
	public void shouldCallUpdateScheduleWithGivenSchedule_whenCallUpdateSchedule() {
		service.updateSchedule(mockSchedule);
		verify(mockScheduleDao).updateSchedule(mockSchedule);
	}
	
	@Test
	public void shouldReturnScheduleReturnedByDao_whenCallUpdateSchedule() {
		Schedule actualSchedule = service.updateSchedule(mockSchedule);
		assertThat(actualSchedule).isEqualTo(mockSchedule);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenScheduleIsNullAndCallUpdateSchedule() {
		service.updateSchedule(null);
	}
}
