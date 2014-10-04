package com.kharkiv.board.filter;

import static org.apache.commons.lang3.CharEncoding.UTF_8;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

public class EncodingFilterTest {
	
	private EncodingFilter filter = new EncodingFilter();
	@Mock
	private ServletRequest mockRequest;
	@Mock
	private ServletResponse mockResponse;
	@Mock
	private FilterChain mockChain;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void shouldSetCharacterEncodingForRequest_whenDoFilter() throws IOException, ServletException {
		filter.doFilter(mockRequest, mockResponse, mockChain);
		verify(mockRequest).setCharacterEncoding(UTF_8);
	}
	
	@Test
	public void shouldSetCharacterEncodingForResponse_whenDoFilter() throws IOException, ServletException {
		filter.doFilter(mockRequest, mockResponse, mockChain);
		verify(mockResponse).setCharacterEncoding(UTF_8);
	}
	
	@Test
	public void shouldCallChainDoFilterAfterEncodingChanging_whenDoFilter() throws IOException, ServletException {
		InOrder order = inOrder(mockRequest, mockResponse, mockChain);
		filter.doFilter(mockRequest, mockResponse, mockChain);
		order.verify(mockChain).doFilter(mockRequest, mockResponse);
		order.verify(mockRequest, never()).setCharacterEncoding(anyString());
		order.verify(mockResponse, never()).setCharacterEncoding(anyString());
	}

}
