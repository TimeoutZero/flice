package com.timeoutzero.flice.core.controller;

import static com.timeoutzero.flice.core.compose.Compose.community;
import static com.timeoutzero.flice.core.compose.Compose.user;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.timeoutzero.flice.core.ApplicationTest;
import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.form.CommunityForm;

public class CommunityControllerTest extends ApplicationTest{

	@Before
	public void before(){
		User marcos = user("marcos.fernandes").build();
		saveAll();
		login(marcos);
	}
	
	@Test
	public void testeListActives() throws Exception{
		Community filmes = community("Filmes").build();
		Community games = community("Games").build();
		Community series = community("Series").active(false).build();
		Community mulheres = community("Mulheres").build();
		saveAll(filmes, games, series, mulheres);
		
		JsonNode json = get("/community").expectedStatus(HttpStatus.OK).getJson();
		jsonAsserter(json)
		.assertThat("$", hasSize(3))
		.assertThat("$.[*].name", contains("Filmes", "Games", "Mulheres"));
	}
	
	@Test
	public void testFindById() throws Exception{
		Community shows = community("Show").build();
		saveAll(shows);
		
		JsonNode json = get("/community/%s", shows.getId()).expectedStatus(HttpStatus.OK).getJson();
		jsonAsserter(json)
		.assertThat("$.name", equalTo("Show"))
		.assertThat("$.description", equalTo("Show"));
	}
	
	@Test
	public void testCreate() throws Exception{
		
		CommunityForm form = new CommunityForm();
		form.setName("Games");
		form.setDescription("Comunidade de games");
		form.setImage("imagem");
		
		JsonNode json = post("/community")
		    .json(form)
		    .expectedStatus(HttpStatus.CREATED)
		    .getJson();
		
		jsonAsserter(json)
		.assertThat("$.name", equalTo("Games"))
		.assertThat("$.description", equalTo("Comunidade de games"))
		.assertThat("$.image", equalTo("imagem"));
	}

	@Test
	public void testUpdate() throws Exception{
		
		Community games = community("Games")
				.description("Comunidade de games").image("imagem").build();
		saveAll(games);
		
		CommunityForm form = new CommunityForm();
		form.setName("Vídeo Games");
		form.setDescription("Comunidade de vídeo games");
		form.setImage("novaimagem.png");
		
		JsonNode json = post("/community")
				.json(form)
				.expectedStatus(HttpStatus.CREATED)
				.getJson();
		
		jsonAsserter(json)
		.assertThat("$.name", equalTo("Vídeo Games"))
		.assertThat("$.description", equalTo("Comunidade de vídeo games"))
		.assertThat("$.image", equalTo("novaimagem.png"));
	}
	
	@Test
	public void testDelete() throws Exception{
		Community games = community("Games")
				.description("Comunidade de games").image("imagem").build();
		saveAll(games);
		
		delete("/community/%s", games.getId()).expectedStatus(HttpStatus.OK);
	}
	
}
