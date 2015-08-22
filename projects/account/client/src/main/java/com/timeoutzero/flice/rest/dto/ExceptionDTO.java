package com.timeoutzero.flice.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionDTO {
	
	private int code;
	private String message;
}
