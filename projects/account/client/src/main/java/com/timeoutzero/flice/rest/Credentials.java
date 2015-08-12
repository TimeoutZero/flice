package com.timeoutzero.flice.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Credentials {

	private static final String CHARSET_UTF_8 = "UTF-8";
	private static final String CLIENT_ID 	  = "clientId";
	private static final String SECRET_KEY 	  = "secretKey";
	
	private String url;
	private String clientId;
	private String secretKey;
	
	public String getUrl(String endpoint) {
		
		List<NameValuePair> parameters = new ArrayList<>();
		
		parameters.add(createQueryStringParam(CLIENT_ID, clientId));
		parameters.add(createQueryStringParam(SECRET_KEY, secretKey));
		
		StringBuilder builderURL = new StringBuilder(url);
		builderURL.append(endpoint);
		builderURL.append("?" + URLEncodedUtils.format(parameters , CHARSET_UTF_8));
	
		return builderURL.toString();
	}

	private BasicNameValuePair createQueryStringParam(String name, String value) {
		return new BasicNameValuePair(name, value);
	}
}
