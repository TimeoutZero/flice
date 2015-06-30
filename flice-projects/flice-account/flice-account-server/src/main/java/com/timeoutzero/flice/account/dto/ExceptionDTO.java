package com.timeoutzero.flice.account.dto;

import lombok.Getter;

public class ExceptionDTO {

	@Getter
	private String message;

	public ExceptionDTO(String code) {
		this.message = code;
	}
}
