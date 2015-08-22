package com.timeoutzero.flice.rest;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeoutzero.flice.rest.dto.ExceptionDTO;

public class CustomErrorHandler implements ResponseErrorHandler {

	private ObjectMapper mapper = new ObjectMapper();
	private ResponseErrorHandler defaultHandler = new DefaultResponseErrorHandler();
	
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return defaultHandler.hasError(response);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		
		String json = IOUtils.toString(response.getBody());
		
		ExceptionDTO exception = mapper.readValue(json, ExceptionDTO.class);
		HttpStatus status = HttpStatus.valueOf(exception.getCode());
		
		throw new AccountException(status, exception.getMessage());
	}

}
