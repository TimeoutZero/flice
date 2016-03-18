package com.timeoutzero.flice.account.controller;

import java.io.IOException;
import java.util.MissingFormatArgumentException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.timeoutzero.flice.account.dto.ExceptionDTO;
import com.timeoutzero.flice.account.exception.AccountException;

@ControllerAdvice
public class ExceptionController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ExceptionController.class);
	
	@ExceptionHandler(AccountException.class)
	public @ResponseBody ExceptionDTO resolve(AccountException e, HttpServletResponse response) throws IOException {
		
		response.setStatus(e.getStatusCode());
		
		return new ExceptionDTO(e.getStatusCode(), e.getMessage());
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public @ResponseBody ExceptionDTO resolve(BadCredentialsException e, HttpServletResponse response) throws IOException {
		return new ExceptionDTO(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
	}
	
	@ExceptionHandler(ValidationException.class)
	public @ResponseBody ExceptionDTO resolveValidationException(MissingFormatArgumentException e, HttpServletResponse response) {
		e.printStackTrace();
		return new ExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "internal.server.error");
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public @ResponseBody ExceptionDTO resolveMissingArgsException(MissingFormatArgumentException e, HttpServletResponse response) {
		e.printStackTrace();
		return new ExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "internal.server.error");
	}
	
	@ExceptionHandler(RuntimeException.class)
	public @ResponseBody ExceptionDTO resolveGenericException(RuntimeException e, HttpServletResponse response) {
		e.printStackTrace();
		return new ExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "internal.server.error");
	}
}
