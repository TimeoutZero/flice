package com.timeoutzero.flice.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionDTO {
	
	private int code;
	private String message;
}
