package com.timeoutzero.flice.account.exception;

import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
public class AccountException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int statusCode;
	private String message;

	public AccountException() {
		super();
	}

	public AccountException(HttpStatus status, String message) {
		super();
		this.statusCode = status.value();
		this.message = message;
	}

}
