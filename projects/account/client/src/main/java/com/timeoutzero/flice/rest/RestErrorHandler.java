package com.timeoutzero.flice.rest;

import java.io.IOException;
import java.io.InputStream;

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
		
		String body = null; 
		
		try { 
			
			InputStream stream = response.getBody();
			body = IOUtils.toString(stream);
			
		} catch (Exception e) {
			e.getLocalizedMessage();
		}

		ExceptionDTO dto = new ObjectMapper().readValue(body, ExceptionDTO.class);
		
		throw new AccountException(HttpStatus.valueOf(dto.getCode()), dto.getMessage());
	}

}
