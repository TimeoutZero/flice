package com.timeoutzero.flice.rest.operations;

import java.util.List;

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
	public <T> T get(String endpoint, Class<?> clazz) {
		return (T) template.exchange(this.getUrl(endpoint), HttpMethod.GET, this.getHttpEntity(), clazz).getBody();
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> list(String endpoint, Class<?> clazz) throws Exception {
		String body = template.exchange(this.getUrl(endpoint), HttpMethod.GET, this.getHttpEntity(), String.class).getBody();
		
		return (List<T>) mapper.readValue(body, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
	}
	
	@SuppressWarnings("unchecked")
	public <T> T post(String endpoint, Object content, Class<?> clazz)  {
		return (T) template.postForEntity(this.getUrl(endpoint), this.getHttpEntity(content), clazz).getBody();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T put(String endpoint, Object content, Class<?> clazz)  {
		return (T) template.exchange(this.getUrl(endpoint), HttpMethod.PUT, this.getHttpEntity(content), clazz);
	}
}
