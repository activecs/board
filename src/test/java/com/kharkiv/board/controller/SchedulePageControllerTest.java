package com.kharkiv.board.controller;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;
import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.service.ScheduleService;

public class SchedulePageControllerTest {
	
	private static final String PAGE_SCHEDULE = "schedule";
	private static final String ATTRIBUTE_SCHEDULES = "schedules";
	
	@Mock
	private RequestDispatcher mockDispatcher;
	@Mock
	private ScheduleService mockService;
	@Mock
	private Schedule mockSchedule;
	@Mock
	private Model mockModel;
	
	@InjectMocks
	private SchedulePageController controller = new SchedulePageController();
	
	@Before
	public void setUp(){
		initMocks(this);
		setupMockBehaviour();
	}

	private void setupMockBehaviour() {
		when(mockService.getAllSchedules()).thenReturn(Lists.newArrayList(mockSchedule));
	}

	@Test
	public void shouldGetAllSchedulesFromService_whenGetPage() throws ServletException, IOException {
		controller.getPage(mockModel);
		verify(mockService).getAllSchedules();
	}
	
	@Test
	public void shouldAddAllSchedulesToRequest_whenGetPage() throws ServletException, IOException {
		controller.getPage(mockModel);
		verify(mockModel).addAttribute(ATTRIBUTE_SCHEDULES, newArrayList(mockSchedule));
	}
	
	@Test
	public void shouldForwardToSchedulePage_whenGetPage() throws ServletException, IOException {
		String actualPage = controller.getPage(mockModel);
		assertThat(actualPage).isEqualTo(PAGE_SCHEDULE);
	}
}
