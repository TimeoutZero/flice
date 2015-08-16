package com.timeoutzero.flice.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Credentials {

	private static final String CHARSET_UTF_8 = "UTF-8";
	private static final String TOKEN 	      = "token";
	
	private String url;
	
	@Getter
	private String token;
	
	public String getUrl(String endpoint) {
		
		List<NameValuePair> parameters = new ArrayList<>();
		
		parameters.add(createQueryStringParam(TOKEN, token));
		
		StringBuilder builderURL = new StringBuilder(url);
		builderURL.append(endpoint);
		builderURL.append("?" + URLEncodedUtils.format(parameters , CHARSET_UTF_8));
	
		return builderURL.toString();
	}
	
	private BasicNameValuePair createQueryStringParam(String name, String value) {
		return new BasicNameValuePair(name, value);
	}
}
