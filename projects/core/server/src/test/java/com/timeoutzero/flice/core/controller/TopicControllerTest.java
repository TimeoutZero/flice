package com.timeoutzero.flice.core.controller;

import static com.timeoutzero.flice.core.compose.Compose.topic;
import static com.timeoutzero.flice.core.compose.Compose.user;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.timeoutzero.flice.core.ApplicationTest;
import com.timeoutzero.flice.core.compose.Compose;
import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.Topic;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.form.TopicForm;

public class TopicControllerTest extends ApplicationTest{

	@Test
	public void testListActives() throws Exception{
		
		Community community = Compose.community("Games").build();
		User marcos = user("marcos.fernandes").build();
		
		saveAll(marcos, community);
		login(marcos);
		
		Topic topico1 = topic(community, "Topico 1").build();
		Topic topico2 = topic(community, "Topico 2").active(false).build();
		Topic topico3 = topic(community, "Topico 3").build();
		Topic topico4 = topic(community, "Topico 4").build();
		
		saveAll(topico1, topico2, topico3, topico4);
		
		JsonNode json = get("/topic")
				.expectedStatus(HttpStatus.OK).getJson();
		
		jsonAsserter(json)
			.assertThat("$", hasSize(3))
			.assertThat("$.[*].name", contains("Topico 1", "Topico 3", "Topico 4"));
	}
	
	@Test
	public void testFindById() throws Exception{
		
		Community community = Compose.community("Games").build();
		User marcos = user("marcos.fernandes").build();
		
		saveAll(marcos, community);
		login(marcos);
		
		Topic gta = topic(community, "GTA V").build();
		saveAll(gta);
		
		JsonNode json = get("/topic/%s", gta.getId())
				.expectedStatus(HttpStatus.OK).getJson();
		
		jsonAsserter(json)
			.assertThat("$.name", equalTo("GTA V"))
			.assertThat("$.communityId", equalTo(community.getId().intValue()));
	}
	
	@Test
	public void testCreate() throws Exception {
		
		Community community = Compose.community("Games").build();
		User marcos = user("marcos.fernandes").build();
		
		saveAll(marcos, community);
		login(marcos);
		
		TopicForm form = new TopicForm();
		form.setCommunityId(community.getId());
		form.setName("FIFA 15");
		
		JsonNode json = post("/topic").json(form).expectedStatus(HttpStatus.CREATED).getJson();
	
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
		
		Topic gta = topic(community, "GTA V").build();
		saveAll(gta);
		
		TopicForm form = new TopicForm();
		form.setCommunityId(community.getId());
		form.setName("FIFA 15");
		
		JsonNode json = put("/topic/%s", gta.getId()).json(form).expectedStatus(HttpStatus.OK).getJson();
		
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
	
		Topic gta = topic(community, "GTA V").build();
		saveAll(gta);
		
		delete("/topic/%s", gta.getId()).expectedStatus(HttpStatus.OK);
	}
	
}
