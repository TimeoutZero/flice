package com.timeoutzero.flice.rest;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeoutzero.flice.rest.dto.ExceptionDTO;

public class RestErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return !response.getStatusCode().is2xxSuccessful();
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		
		String string = IOUtils.toString(response.getBody());
		ExceptionDTO dto = new ObjectMapper().readValue(string, ExceptionDTO.class);
		
		HttpStatus statusCode = response.getStatusCode();
		System.out.println();
		System.out.println("deu ruim aqui status: " + statusCode.value());
		System.out.println();
		throw new AccountException(statusCode, dto.getMessage());
	}

}
