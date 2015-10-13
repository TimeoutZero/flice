package com.timeoutzero.flice.rest.operations;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.timeoutzero.flice.rest.CustomErrorHandler;

public class FliceTemplate extends AbstractFliceTemplate {
	
	private RestTemplate template;
	
	public FliceTemplate(String url, String applicationSecretKey) {
		super(url, applicationSecretKey);
		
		this.template = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		this.template.setErrorHandler(new CustomErrorHandler());	
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String endpoint, Class<?> class1) {
		return (T) template.exchange(this.getUrl(endpoint), HttpMethod.GET, this.getHttpEntity(), class1).getBody();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T post(String endpoint, Object content, Class<?> clazz)  {
		return (T) template.postForEntity(this.getUrl(endpoint), this.getHttpEntity(content), clazz).getBody();
	}
}
