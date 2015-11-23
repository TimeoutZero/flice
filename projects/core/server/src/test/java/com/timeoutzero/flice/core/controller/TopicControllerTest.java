package com.timeoutzero.flice.core.controller;

import static com.timeoutzero.flice.core.compose.Compose.topic;
import static com.timeoutzero.flice.core.compose.Compose.user;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.util.Arrays;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.timeoutzero.flice.core.ApplicationTest;
import com.timeoutzero.flice.core.compose.Compose;
import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.Topic;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.form.TopicForm;
import com.timeoutzero.flice.core.service.CoreService;
import com.timeoutzero.flice.rest.dto.AccountUserDTO;
import com.timeoutzero.flice.rest.operations.AccountOperations;
import com.timeoutzero.flice.rest.operations.UserOperations;

public class TopicControllerTest extends ApplicationTest {

	@Mock
	private AccountOperations accountOperations;
	
	@Autowired
	@InjectMocks
	private CoreService coreService;
	
	@Test
	public void testListActives() throws Exception{
		
		Community community = Compose.community("Games").build();
		User marcos = user("marcos.fernandes").build();
		
		saveAll(marcos, community);
		login(marcos);
		
		Topic topico1 = topic("Topico 1", community, marcos).build();
		Topic topico2 = topic("Topico 2", community, marcos).active(false).build();
		Topic topico3 = topic("Topico 3", community, marcos).build();
		Topic topico4 = topic("Topico 4", community, marcos).build();
		
		saveAll(topico1, topico2, topico3, topico4);

		AccountUserDTO dto = new AccountUserDTO();
		dto.setId(marcos.getId());
		
		Mockito.when(this.accountOperations.getUserOperations()).thenReturn(Mockito.mock(UserOperations.class));
		Mockito.when(this.accountOperations.getUserOperations().list(Mockito.any())).thenReturn(Arrays.asList(dto));
		
		JsonNode json = get("/community/%s/topic", community.getId())
				.expectedStatus(HttpStatus.OK).getJson();
		
		jsonAsserter(json)
			.assertThat("$", hasSize(3))
			.assertThat("$.[*].name", contains("Topico 1", "Topico 3", "Topico 4"));
	}
	
	@Test
	public void testFindById() throws Exception{
		
		User marcos = user("marcos.fernandes").build();
		saveAll(marcos);
		login(marcos);

		Community community = Compose.community("Games").owner(marcos).build();
		saveAll(community);
		
		Topic gta = topic("GTA V", community, marcos).build();
		saveAll(gta);
		
		JsonNode json = get("community/%s/topic/%s", community.getId(), gta.getId())
				.expectedStatus(HttpStatus.OK).getJson();
		
		jsonAsserter(json)
			.assertEquals("$.name", "GTA V")
			.assertThat("$.communityId", equalTo(community.getId().intValue()));
	}
	
	@Test
	public void testCreate() throws Exception {
		
		Community community = Compose.community("Games").build();
		User marcos = user("marcos.fernandes").build();
		
		saveAll(marcos, community);
		login(marcos);
		
		TopicForm form = new TopicForm();
		form.setName("FIFA 15");
		form.setContent(RandomStringUtils.randomAlphabetic(10));
		
		JsonNode json = post("/community/%s/topic", community.getId()).
				json(form).expectedStatus(HttpStatus.CREATED).getJson();
	
		jsonAsserter(json)
			.assertThat("$.name", equalTo("FIFA 15"))
			.assertThat("$.communityId", equalTo(community.getId().intValue()));
	}

	@Test
	public void testUpdate() throws Exception {
		
		Community community = Compose.community("Games").build();
		User marcos = user("marcos.fernandes").build();
		
		saveAll(marcos, community);
		login(marcos);
		
		Topic gta = topic("GTA V", community, marcos).build();
		saveAll(gta);
		
		TopicForm form = new TopicForm();
		form.setName("FIFA 15");
		form.setContent("Content");
		
		JsonNode json = put("/community/%s/topic/%s", community.getId(), gta.getId()).json(form).expectedStatus(HttpStatus.OK).getJson();
		
		jsonAsserter(json)
			.assertThat("$.name", equalTo("FIFA 15"))
			.assertThat("$.communityId", equalTo(community.getId().intValue()));
	}
	
	@Test
	public void testDelete() throws Exception {
		
		Community community = Compose.community("Games").build();
		User marcos = user("marcos.fernandes").build();
		
		saveAll(marcos, community);
		login(marcos);
	
		Topic gta = topic("GTA V", community, marcos).build();
		saveAll(gta);
		
		delete("community/%s/topic/%s", community.getId(), gta.getId()).expectedStatus(HttpStatus.OK);
	}
	
}
