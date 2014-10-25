package com.kharkiv.board.controller;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

public class BaseControllerTest {
	
	private static final String REQUEST_URI_VALUE = "requestUriValue";
	private static final String REQUEST_URI = "requestUri";
	private static final String EXCEPTION = "exception";
	private static final String ERROR_PAGE = "error";
	
	private BaseController controller = new BaseController();
	@Mock
	private HttpServletRequest mockRequest;
	@Mock
	private NoHandlerFoundException mockPageNotFoundException;
	private Exception exception = new Exception();
	
	
	@Before
	public void setUp() {
		initMocks(this);
		when(mockRequest.getRequestURI()).thenReturn(REQUEST_URI_VALUE);
	}

	@Test
	public void shouldSetErrorPageAsView_whenHandleException() {
		ModelAndView model = controller.handleException(exception);
		assertThat(model.getViewName()).isEqualTo(ERROR_PAGE);
	}
	
	@Test
	public void shouldSetExceptionToModel_whenHandleException() {
		ModelAndView model = controller.handleException(exception);
		assertThat(model.getModelMap().get(EXCEPTION)).isEqualTo(exception);
	}
	
	@Test
	public void shouldSetErrorPageAsView_whenRequestHandlingNoHandlerFound() {
		ModelAndView model = controller.requestHandlingNoHandlerFound(mockRequest, mockPageNotFoundException);
		assertThat(model.getViewName()).isEqualTo(ERROR_PAGE);
	}
	
	@Test
	public void shouldSetRequestUriToModel_whenRequestHandlingNoHandlerFound() {
		ModelAndView model = controller.requestHandlingNoHandlerFound(mockRequest, mockPageNotFoundException);
		assertThat(model.getModelMap().get(REQUEST_URI)).isEqualTo(REQUEST_URI_VALUE);
	}
	
}
