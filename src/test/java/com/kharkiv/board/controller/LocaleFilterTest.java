package com.kharkiv.board.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.kharkiv.board.filter.LocaleFilter;

public class LocaleFilterTest {

    private static final String LANG_PARAM = "ru";
    private static final String LANG_PARAMETER = "lang";
    
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @Mock
    private HttpSession session;

    private LocaleFilter locFilter = new LocaleFilter();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCallDoFilterMethod_whenLocaleParameterIsNullAndSessionIsNullAndCallMethodDoFilter() throws IOException, ServletException {
        locFilter.doFilter(request, response, chain);
        verify(request).getParameter(LANG_PARAMETER);
        verify(request).getSession();
        verify(chain).doFilter(request, response);
    }
    
    @Test
    public void shouldCallDoFilterMethod_whenLocaleParameterIsNotNullAndSessionIsNullAndCallMethodDoFilter() throws IOException, ServletException {
        when(request.getParameter(anyString())).thenReturn(LANG_PARAM);
        locFilter.doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
    }
    
    @Test
    public void sshouldCallDoFilterMethod_whenLocaleParameterIsNullAndSessionIsNotNullAndCallMethodDoFilter() throws IOException, ServletException {
        when(request.getSession()).thenReturn(session);
        locFilter.doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
    }
    
    @Test
    public void sshouldNotCallDoFilterMethod_whenLocaleParameterIsNotNullAndSessionIsNotNullAndCallMethodDoFilter() throws IOException, ServletException {
        when(request.getParameter(anyString())).thenReturn(LANG_PARAM);
        when(request.getSession()).thenReturn(session);
        locFilter.doFilter(request, response, chain);
        verify(chain, never()).doFilter(request, response);
    }
}
