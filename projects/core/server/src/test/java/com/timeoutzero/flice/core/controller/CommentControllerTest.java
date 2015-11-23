package com.timeoutzero.flice.core.controller;

import static com.timeoutzero.flice.core.compose.Compose.comment;
import static com.timeoutzero.flice.core.compose.Compose.topic;
import static com.timeoutzero.flice.core.compose.Compose.user;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.util.Arrays;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.timeoutzero.flice.core.ApplicationTest;
import com.timeoutzero.flice.core.compose.Compose;
import com.timeoutzero.flice.core.domain.Comment;
import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.Topic;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.form.CommentForm;
import com.timeoutzero.flice.core.service.CoreService;
import com.timeoutzero.flice.rest.dto.AccountUserDTO;
import com.timeoutzero.flice.rest.operations.AccountOperations;
import com.timeoutzero.flice.rest.operations.UserOperations;

public class CommentControllerTest extends ApplicationTest{

	@Mock
	private AccountOperations accountOperations;
	
	@Autowired
	@InjectMocks
	private CoreService coreService;
	
	private Topic topic;
	private User marcos;
	
	@Before
	public void before(){
		
		marcos = user("marcos.fernandes").build();
		Community community = Compose.community("Seriados").build();
	
		saveAll(marcos, community);
		login(marcos);
		
		topic = topic("TWD", community, marcos).build();
		saveAll(topic);
	}
	
	@Test
	public void testListPageable() throws Exception{ 
		 
		Comment comentario1 = comment("comentario 1", topic, marcos).created(DateTime.now().minusSeconds(3)).build();
		Comment comentario2 = comment("comentario 2", topic, marcos).created(DateTime.now().minusSeconds(2)).build();
		Comment comentario3 = comment("comentario 3", topic, marcos).created(DateTime.now().minusSeconds(1)).build();
		Comment comentario4 = comment("comentario 4", topic, marcos).build();
		
		saveAll(comentario1, comentario2, comentario3, comentario4);
		
		AccountUserDTO dto = new AccountUserDTO();
		dto.setId(marcos.getId());
		
		Mockito.when(this.accountOperations.getUserOperations()).thenReturn(Mockito.mock(UserOperations.class));
		Mockito.when(this.accountOperations.getUserOperations().list(Mockito.any())).thenReturn(Arrays.asList(dto));

		JsonNode json = get("/topic/%s/comment", topic.getId())
				.queryParam("page", "0") 
				.queryParam("size", "10")
				.expectedStatus(HttpStatus.OK)
				.getJson();
		
		jsonAsserter(json)
			.assertThat("$", hasSize(4))
			.assertThat("$.[*].content", contains("comentario 1", "comentario 2", "comentario 3", "comentario 4"));
	}
	
	@Test
	public void testFindById() throws Exception{
		
		Comment comentario = comment("comentario", topic, marcos).build();
		saveAll(comentario);
		
		JsonNode json = get("/topic/%s/comment/%s", topic.getId(), comentario.getId()).expectedStatus(HttpStatus.OK).getJson();
		
		jsonAsserter(json)
			.assertThat("$.content", equalTo("comentario"))
			.assertThat("$.topicId", equalTo(topic.getId().intValue()));
	}
	
	@Test
	public void testCreate() throws Exception {
		
		CommentForm form = new CommentForm();
		form.setContent("comentario");
		
		JsonNode json = post("/topic/%s/comment", topic.getId()).json(form).expectedStatus(HttpStatus.CREATED).getJson();
		
		jsonAsserter(json)
			.assertThat("$.content", equalTo("comentario"))
			.assertThat("$.topicId", equalTo(topic.getId().intValue()));
	}

	@Test
	public void testUpdate() throws Exception {
		
		Comment comentario = comment("comentario", topic, marcos).build();
		saveAll(comentario);
		
		CommentForm form = new CommentForm();
		form.setContent("comentario editado");
		
		JsonNode json = put("/topic/%s/comment/%s", topic.getId(), comentario.getId()).json(form).expectedStatus(HttpStatus.OK).getJson();
		
		jsonAsserter(json)
			.assertThat("$.content", equalTo("comentario editado"))
			.assertThat("$.topicId", equalTo(topic.getId().intValue()));
	}

	@Test
	public void testDelete() throws Exception {
		
		Comment comentario = comment("comentario", topic, marcos).build();
		saveAll(comentario);
		
		delete("/topic/%s/comment/%s", topic.getId(), comentario.getId()).expectedStatus(HttpStatus.OK);
	}
}
