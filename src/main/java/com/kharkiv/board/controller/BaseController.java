package com.kharkiv.board.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice
public class BaseController {
	
	private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
	private static final String REQUEST_URI = "requestUri";
	private static final String EXCEPTION = "exception";
	private static final String ERROR_PAGE = "error";

	@ExceptionHandler(Exception.class)
	@ResponseStatus(INTERNAL_SERVER_ERROR)
	public ModelAndView handleException(Exception ex) {
		ModelAndView model = new ModelAndView();
		LOG.trace(ex.getMessage(), ex);
		model.setViewName(ERROR_PAGE);
		model.addObject(EXCEPTION, ex);
		return model;
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = NOT_FOUND)
	public ModelAndView requestHandlingNoHandlerFound(HttpServletRequest request, NoHandlerFoundException ex) {
		ModelAndView model = new ModelAndView();
		LOG.trace(ex.getMessage(), ex);
		model.setViewName(ERROR_PAGE);
		model.addObject(REQUEST_URI, request.getRequestURI());
		return model;
	}
}
