package com.timeoutzero.flice.core.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.timeoutzero.flice.rest.AccountException;
import com.timeoutzero.flice.rest.dto.ExceptionDTO;

@ControllerAdvice
public class ExceptionController {

	private static Logger log = LoggerFactory.getLogger(ExceptionController.class);
	
	@ExceptionHandler(RuntimeException.class)
	public void name(AccountException e, HttpServletResponse response) {
		
		log.error(e.getLocalizedMessage());
		response.setStatus(e.getStatusCode());
	}
	
	@ExceptionHandler(AccountException.class)
	public @ResponseBody ExceptionDTO resolve(AccountException e, HttpServletResponse response) {

		log.error(e.getLocalizedMessage());
		
		response.setStatus(e.getStatusCode());
		
		return new ExceptionDTO(e.getStatusCode(), e.getMessage());
	}
}
