package com.timeoutzero.flice.account.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.timeoutzero.flice.account.dto.ExceptionDTO;
import com.timeoutzero.flice.account.exception.AccountException;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(AccountException.class)
	public @ResponseBody ExceptionDTO resolve(AccountException e, HttpServletResponse response) {
		response.setStatus(e.getStatusCode());
		return new ExceptionDTO(e.getMessage());
	}
}
