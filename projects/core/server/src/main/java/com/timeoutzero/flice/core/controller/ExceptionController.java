package com.timeoutzero.flice.core.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.timeoutzero.flice.core.exception.WebException;
import com.timeoutzero.flice.rest.AccountException;
import com.timeoutzero.flice.rest.dto.ExceptionDTO;

@ControllerAdvice
public class ExceptionController {

	private static Logger LOG = LoggerFactory.getLogger(ExceptionController.class);

	private static final String EXCEPTION_ACCESS_DENIED = "Access denied!";
	
	
	@ResponseBody
	@ExceptionHandler(value = WebException.class)
	public ExceptionDTO webException(WebException exception, HttpServletResponse response ) {
		
		response.setStatus(exception.getStatus().value());
		return new ExceptionDTO(exception.getStatus().value(), exception.getLocalizedMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(value = AccessDeniedException.class)
	public ExceptionDTO resolveAccessDenied(AccessDeniedException exception, HttpServletResponse response) {
		
		LOG.error(exception.getLocalizedMessage());
		response.setStatus(HttpStatus.FORBIDDEN.value());
	
		return new ExceptionDTO(HttpStatus.FORBIDDEN.value(), EXCEPTION_ACCESS_DENIED);
	}

	@ResponseBody
	@ExceptionHandler(value = AccountException.class)
	public ExceptionDTO resolveAccountException(AccountException e, HttpServletResponse response) {
		
		LOG.error(e.getLocalizedMessage());
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		return new ExceptionDTO(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(value = RuntimeException.class)
	public ExceptionDTO resolveRuntimeException(RuntimeException exception, HttpServletResponse response) {
		 
		LOG.error(exception.getLocalizedMessage());
		return new ExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getLocalizedMessage());
	}
	
}
