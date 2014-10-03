package com.kharkiv.board.controller;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.google.common.collect.Lists;
import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.service.ScheduleService;

public class SchedulePageControllerTest {
	
	private static final String PAGE_SCHEDULE = "/WEB-INF/pages/schedule.jsp";
	private static final String ATTRIBUTE_SCHEDULES = "schedules";
	
	@Mock
	private HttpServletRequest mockRequest;
	@Mock
	private HttpServletResponse mockResponse;
	@Mock
	private RequestDispatcher mockDispatcher;
	@Mock
	private ScheduleService mockService;
	@Mock
	private Schedule mockSchedule;
	
	@InjectMocks
	private SchedulePageController servlet = new SchedulePageController();
	
	@Before
	public void setUp(){
		initMocks(this);
		setupMockBehaviour();
	}

	private void setupMockBehaviour() {
		when(mockRequest.getRequestDispatcher(PAGE_SCHEDULE)).thenReturn(mockDispatcher);
		when(mockService.getAllSchedules()).thenReturn(Lists.newArrayList(mockSchedule));
	}

	@Test
	public void shouldGetDispatcherFromRequest_whenCallDoGet() throws ServletException, IOException {
		servlet.doGet(mockRequest, mockResponse);
		verify(mockRequest).getRequestDispatcher(PAGE_SCHEDULE);
	}
	
	@Test
	public void shouldGetAllSchedulesFromService_whenCallDoGet() throws ServletException, IOException {
		servlet.doGet(mockRequest, mockResponse);
		verify(mockService).getAllSchedules();
	}
	
	@Test
	public void shouldAddAllSchedulesToRequest_whenCallDoGet() throws ServletException, IOException {
		servlet.doGet(mockRequest, mockResponse);
		verify(mockRequest).setAttribute(ATTRIBUTE_SCHEDULES, newArrayList(mockSchedule));
	}
	
	@Test
	public void shouldForwardRequestWithGivenRequestAndResponse_whenCallDoGet() throws ServletException, IOException {
		servlet.doGet(mockRequest, mockResponse);
		verify(mockDispatcher).forward(mockRequest, mockResponse);
	}
}
