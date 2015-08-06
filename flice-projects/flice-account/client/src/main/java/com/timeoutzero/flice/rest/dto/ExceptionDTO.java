package com.timeoutzero.flice.rest.dto;

import lombok.Getter;

public class ExceptionDTO {

	@Getter
	private String message;

	public ExceptionDTO(String code) {
		this.message = code;
	}
}
