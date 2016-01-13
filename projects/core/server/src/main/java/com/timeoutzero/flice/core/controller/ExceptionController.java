package com.timeoutzero.flice.core.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.timeoutzero.flice.rest.AccountException;
import com.timeoutzero.flice.rest.dto.ExceptionDTO;

@ControllerAdvice
public class ExceptionController {

	private static Logger LOG = LoggerFactory.getLogger(ExceptionController.class);
	
	@ResponseBody
	@ExceptionHandler(RuntimeException.class)
	public ExceptionDTO resolveRuntimeException(RuntimeException exception, HttpServletResponse response) {
		 
		LOG.error(exception.getLocalizedMessage());
		
		//response.setStatus();
		return new ExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getLocalizedMessage());
	}
	
	@ExceptionHandler(AccountException.class)
	public @ResponseBody ExceptionDTO resolve(AccountException e, HttpServletResponse response) {

		LOG.error(e.getLocalizedMessage());
		
		response.setStatus(e.getStatusCode());
		
		return new ExceptionDTO(e.getStatusCode(), e.getMessage());
	}
}
