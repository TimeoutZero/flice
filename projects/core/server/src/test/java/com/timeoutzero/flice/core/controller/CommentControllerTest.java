package com.timeoutzero.flice.core.controller;

import static com.timeoutzero.flice.core.compose.Compose.comment;
import static com.timeoutzero.flice.core.compose.Compose.topic;
import static com.timeoutzero.flice.core.compose.Compose.user;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.timeoutzero.flice.core.ApplicationTest;
import com.timeoutzero.flice.core.compose.Compose;
import com.timeoutzero.flice.core.domain.Comment;
import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.Topic;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.form.CommentForm;

public class CommentControllerTest extends ApplicationTest{

	Topic topic;
	
	@Before
	public void before(){
		User marcos = user("marcos.fernandes").build();
		Community community = Compose.community("Seriados").build();
		saveAll(marcos, community);
		login(marcos);
		topic = topic(community, "TWD").build();
		saveAll(topic);
	}
	
	@Test
	public void testFindActives() throws Exception{
		Comment comentario1 = comment(topic, "comentario 1").build();
		Comment comentario2 = comment(topic, "comentario 2").active(false).build();
		Comment comentario3 = comment(topic, "comentario 3").build();
		Comment comentario4 = comment(topic, "comentario 4").build();
		Comment comentario5 = comment(topic, "comentario 5").build();
		saveAll(comentario1, comentario2, comentario3, comentario4, comentario5);
		
		JsonNode json = get("/comment").expectedStatus(HttpStatus.OK).getJson();
		jsonAsserter(json)
		.assertThat("$", hasSize(4))
		.assertThat("$.[*].content", contains("comentario 1", "comentario 3", "comentario 4", "comentario 5"));
	}
	
	@Test
	public void testFindById() throws Exception{
		Comment comentario = comment(topic, "comentario").build();
		saveAll(comentario);
		
		JsonNode json = get("/comment/%s", comentario.getId()).expectedStatus(HttpStatus.OK).getJson();
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
		Comment comentario = comment(topic, "comentario").build();
		saveAll(comentario);
		
		CommentForm form = new CommentForm();
		form.setContent("comentario editado");
		
		JsonNode json = put("topic/%s/comment/%s", topic.getId(), comentario.getId()).json(form).expectedStatus(HttpStatus.OK).getJson();
		jsonAsserter(json)
		.assertThat("$.content", equalTo("comentario editado"))
		.assertThat("$.topicId", equalTo(topic.getId().intValue()));
	}

	@Test
	public void testDelete() throws Exception {
		Comment comentario = comment(topic, "comentario").build();
		saveAll(comentario);
		
		delete("/comment/%s", comentario.getId()).expectedStatus(HttpStatus.OK);
	}
}
