package com.kharkiv.board.controller;

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

public class IToDoItemServletTest {
	
	private static final String PAGE_INDEX = "/WEB-INF/pages/index.jsp";
	
	@Mock
	private HttpServletRequest mockRequest;
	@Mock
	private HttpServletResponse mockResponse;
	@Mock
	private RequestDispatcher mockDispatcher;
	
	@InjectMocks
	private IToDoItemServlet servlet = new IToDoItemServlet();
	
	@Before
	public void setUp(){
		initMocks(this);
		setupMockBehaviour();
	}

	private void setupMockBehaviour() {
		when(mockRequest.getRequestDispatcher(PAGE_INDEX)).thenReturn(mockDispatcher);
	}

	@Test
	public void shouldGetDispatcherFromRequest_whenCallDoGet() throws ServletException, IOException {
		servlet.doGet(mockRequest, mockResponse);
		verify(mockRequest).getRequestDispatcher(PAGE_INDEX);
	}
	
	@Test
	public void shouldForwardRequestWithGivenRequestAndResponse_whenCallDoGet() throws ServletException, IOException {
		servlet.doGet(mockRequest, mockResponse);
		verify(mockDispatcher).forward(mockRequest, mockResponse);
	}
}