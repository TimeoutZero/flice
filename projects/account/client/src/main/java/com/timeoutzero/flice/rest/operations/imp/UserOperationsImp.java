package com.timeoutzero.flice.rest.operations.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.util.UriComponentsBuilder;

import com.timeoutzero.flice.rest.dto.AccountUserDTO;
import com.timeoutzero.flice.rest.operations.FliceTemplate;
import com.timeoutzero.flice.rest.operations.UserOperations;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserOperationsImp implements UserOperations {
	
	private static final String ENDPOINT = "/user";
	
	private static final String PARAMETER_TOKEN 	= "token";
	private static final String PARAMETER_IDS 		= "ids";
	private static final String PARAMETER_EMAIL 	= "email";
	private static final String PARAMETER_PASSWORD  = "password";
	
	private FliceTemplate template;

	@Override
	public AccountUserDTO get(String token) {
		
		String uri = UriComponentsBuilder.fromUriString(ENDPOINT + "/token")
				.queryParam(PARAMETER_TOKEN, token)
				.build().toUriString();
		
		return template.get(uri, AccountUserDTO.class);
	}
	
	@Override
	public AccountUserDTO get(Long id) {
		return template.get(ENDPOINT + "/" + id , AccountUserDTO.class);
	}

	@Override
	public List<AccountUserDTO> list(List<Long> ids) {
		
		UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(ENDPOINT);

		for (Long id : ids) {
			uri.queryParam(PARAMETER_IDS, id);
		}
		
		try {
			return template.list(uri.build().toUriString(), AccountUserDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public AccountUserDTO create(String email, String password) {
		
		Map<String, String> parameters = new HashMap<>();
		parameters.put(PARAMETER_EMAIL, email);
		parameters.put(PARAMETER_PASSWORD, password);
		  
		return template.post(ENDPOINT, parameters, AccountUserDTO.class);
	}



}
