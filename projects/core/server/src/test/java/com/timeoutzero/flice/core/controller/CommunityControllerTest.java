package com.timeoutzero.flice.core.controller;

import static com.timeoutzero.flice.core.compose.Compose.community;
import static com.timeoutzero.flice.core.compose.Compose.user;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.timeoutzero.flice.core.ApplicationTest;
import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.form.CommunityForm;

public class CommunityControllerTest extends ApplicationTest {
	
	@Test
	public void testListMyCommunitys() throws Exception { 
		
		Community movies   = community("Movies").build();
		Community games    = community("Games").build();
		Community series   = community("Series").build();
		Community politic  = community("Politic").build();

		saveAll(movies, games, series, politic);
		
		User lucas = user("lucas.martins")
				.communitys(Arrays.asList(movies, games, series))
				.build();
	
		saveAll(lucas);
		login(lucas);
		
		JsonNode json = get("/community")
				.expectedStatus(HttpStatus.OK)
				.getJson();
		
		jsonAsserter(json)
			.assertThat("$", hasSize(3))
			.assertThat("$.[*].name", contains("Movies", "Games", "Series"));
	}
	
	@Test
	public void testeListActivesNotSignIn() throws Exception{
		
		Community filmes   = community("Filmes").build();
		Community games    = community("Games").build();
		Community mulheres = community("Mulheres").build();

		saveAll(filmes, games, mulheres);

		JsonNode json = get("/community")
				.expectedStatus(HttpStatus.OK).getJson();
		
		jsonAsserter(json)
			.assertThat("$", hasSize(3))
			.assertThat("$.[*].name", contains("Filmes", "Games", "Mulheres"));
	}
	
	@Test
	public void testFindById() throws Exception{
		
		User marcos = user("marcos.fernandes").build();
		saveAll(marcos);
		login(marcos);

		Community shows = community("Show").members(Arrays.asList(marcos)).build();
		saveAll(shows);
		
		JsonNode json = get("/community/%s", shows.getId())
				.expectedStatus(HttpStatus.OK).getJson();
		
		jsonAsserter(json)
			.assertThat("$.name", equalTo("Show"))
			.assertThat("$.description", equalTo("Show"))
			.assertEquals("$.member", true);
	}
	
	@Test
	public void testJoinCommunity() throws Exception {

		User marcos = user("marcos.fernandes").build();
		saveAll(marcos);
		login(marcos);

		Community shows = community("Show").build();
		saveAll(shows);
		
		put("/community/%s/join", shows.getId()).expectedStatus(HttpStatus.OK).getJson();
		
		jsonAsserter(get("/community/%s", shows.getId()).getJson())
			.assertThat("$.name", equalTo("Show"))
			.assertThat("$.description", equalTo("Show"))
			.assertEquals("$.member", true);
		
	}
	
	@Test
	public void testCreate() throws Exception{
		
		CommunityForm form = new CommunityForm();
		form.setName("Games");
		form.setDescription("Comunidade de games");
		form.setImage("imagem");
		form.setVisibility(false);
		
		User marcos = user("marcos.fernandes").build();
		saveAll(marcos);
		login(marcos);
		
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

		User lucas = user("lucas.martins").build(); 
		saveAll(lucas);
		login(lucas);
		
		Community games = community("Games")
				.description("Comunidade de games")
				.image("imagem")
				.owner(lucas)
				.build();
		
		saveAll(games);
		
		CommunityForm form = new CommunityForm();
		form.setName("Vídeo Games");
		form.setDescription("Comunidade de vídeo games");
		form.setImage("novaimagem.png");
		form.setVisibility(false);
		
		JsonNode json = post("/community")
				.json(form)
				.expectedStatus(HttpStatus.CREATED)
				.getJson();
		
		jsonAsserter(json)
			.assertEquals("$.name", "Vídeo Games")
			.assertEquals("$.description", "Comunidade de vídeo games")
			.assertEquals("$.image", "novaimagem.png");
	}
	
	@Test
	public void testDelete() throws Exception{
		
		Community games = community("Games")
				.description("Comunidade de games")
				.image("imagem").build();
		
		saveAll(games);
		
		User marcos = user("marcos.fernandes").build();
		saveAll(marcos);
		login(marcos);
		
		delete("/community/%s", games.getId())
			.expectedStatus(HttpStatus.OK);
	}
	
}
