package com.timeoutzero.flice.core.controller;

import static com.timeoutzero.flice.core.compose.Compose.community;
import static com.timeoutzero.flice.core.compose.Compose.tag;
import static com.timeoutzero.flice.core.compose.Compose.user;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.timeoutzero.flice.core.ApplicationTest;
import com.timeoutzero.flice.core.RequestBuilder;
import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.Community.Privacy;
import com.timeoutzero.flice.core.domain.Tag;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.form.CommunityForm;
import com.timeoutzero.flice.core.form.TagForm;
import com.timeoutzero.flice.core.service.ImageService;
import com.timeoutzero.flice.core.util.ImageMockUtil;

public class CommunityControllerTest extends ApplicationTest {
	
	@Autowired
	@InjectMocks
	private ImageService imageService;
	
	@Mock
	private AmazonS3Client amazons3;
	
	@Test
	public void testListMyCommunitys() throws Exception { 
		
		User bruno = user("bruno").build();
		saveAll(bruno);
		
		Community movies   = community(bruno, "Movies").build();
		Community games    = community(bruno, "Games").build();
		Community series   = community(bruno, "Series").build();
		Community politic  = community(bruno, "Politic").build();

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
		
		User lucas = user("lucas.martins").build();
		saveAll(lucas);
		
		Community filmes   = community(lucas, "Filmes").build();
		Community games    = community(lucas, "Games").build();
		Community mulheres = community(lucas, "Mulheres").build();

		saveAll(filmes, games, mulheres);
		anonymous();
		
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

		Community shows = community(marcos, "Show").members(Arrays.asList(marcos)).owner(marcos).build();
		saveAll(shows);
		
		JsonNode json = get("/community/%s", shows.getId())
				.expectedStatus(HttpStatus.OK).getJson();
		
		jsonAsserter(json)
			.assertThat("$.name", equalTo("Show"))
			.assertThat("$.description", equalTo("Show"));
	}
	
	@Test
	public void testFindByAutocomplete() throws Exception {
	
		User lucas = user("lucas.martins").build();
		saveAll(lucas);
		login(lucas); 

		Community community1 = community(lucas, "NFL").members(Arrays.asList(lucas)).owner(lucas).build();
		Community community2 = community(lucas, "NFL Patriots").members(Arrays.asList(lucas)).owner(lucas).build();
		Community community3 = community(lucas, "Show").members(Arrays.asList(lucas)).owner(lucas).build();
		
		saveAll(community1, community2, community3);
		
		RequestBuilder request = get("/community/autocomplete");
		request.queryParam("word", "NFL");
		
		JsonNode json = request
				.expectedStatus(HttpStatus.OK).getJson();
		
		jsonAsserter(json)
			.assertThat("$", hasSize(2))
			.assertThat("$.[*].name", contains("NFL", "NFL Patriots"));
	}
	
	@Test
	public void testJoinCommunity() throws Exception {

		User marcos = user("marcos.fernandes").build();
		User lucas = user("lucas.martins").build();
		
		saveAll(marcos, lucas);
		login(marcos);

		Community shows = community(lucas, "Show").build();
		saveAll(shows);
		
		put("/community/%s/join", shows.getId()).expectedStatus(HttpStatus.OK).getJson();
		
		jsonAsserter(get("/community/%s/member/info", shows.getId()).getJson())
			.assertEquals("$.member", true)
			.assertEquals("$.owner", false);
	}
	
	@Test
	public void testCreate() throws Exception {
		
		Mockito.when(amazons3.putObject(Mockito.any(PutObjectRequest.class))).thenReturn(new PutObjectResult());

		Tag tag1 = tag("Games").build();
		Tag tag2 = tag("Youtubers").build();
		
		saveAll(tag1, tag2);
		
		CommunityForm form = new CommunityForm();
		form.setName("4Play");
		form.setDescription("Comunidade de games");
		form.setImage("imagem");
		form.setPrivacy(Privacy.PUBLIC);
		form.setImage(ImageMockUtil.getMockBase64Image());
		form.setCover(ImageMockUtil.getMockBase64Image());
		
		TagForm tagForm1 = new TagForm(tag1.getId(), tag2.getName());
		TagForm tagForm2 = new TagForm(tag2.getId(), tag2.getName());
		TagForm tagForm3 = new TagForm(null, "4Players");
		
		form.setTags(new HashSet<>(Arrays.asList(tagForm1, tagForm2, tagForm3)));
		
		User lucas = user("lucas.martins").build();
		saveAll(lucas);
		login(lucas);
		
		JsonNode json = post("/community")
		    .json(form)
		    .expectedStatus(HttpStatus.CREATED)
		    .getJson();
		
		jsonAsserter(json)
			.assertEquals("$.name", "4Play")
			.assertEquals("$.description", "Comunidade de games")
			.assertEquals("$.privacy", Privacy.PUBLIC.toString())
			.assertNotNull("$.image")
			.assertNotNull("$.cover")
			.assertThat("$.tags.[*]", hasSize(3))
			.assertThat("$.tags.[*].id", hasItems(tag1.getId().intValue(), tag2.getId().intValue()))
			.assertThat("$.tags.[*].name", hasItems(tag1.getName(), tag2.getName(), "4Players"));
	}

	@Test
	public void testUpdate() throws Exception{
		
		Mockito.when(amazons3.putObject(Mockito.any(PutObjectRequest.class))).thenReturn(new PutObjectResult());

		Tag tag1 = tag("Games").build();
		Tag tag2 = tag("Youtubers").build();
		
		saveAll(tag1, tag2);
		
		User lucas = user("lucas.martins").build(); 
		saveAll(lucas);
		login(lucas);
		
		Community games = community(lucas, "Games")
				.description("Comunidade de games")
				.image("imagem")
				.owner(lucas)
				.image("/tmp/community/image")
				.cover("/tmp/community/cover")
				.tags(new HashSet<>(Arrays.asList(tag1, tag2)))
				.build();
		
		saveAll(games);
		
		CommunityForm form = new CommunityForm();
		form.setName("Vídeo Games");
		form.setDescription("Comunidade de vídeo games");
		form.setImage("novaimagem.png");
		form.setPrivacy(Privacy.PUBLIC);
		form.setImage(ImageMockUtil.getMockBase64Image());
		form.setCover(ImageMockUtil.getMockBase64Image());
		
		TagForm tagForm1 = new TagForm(tag1.getId(), tag2.getName());
		TagForm tagForm2 = new TagForm(tag2.getId(), tag2.getName());
		TagForm tagForm3 = new TagForm(null, "4Players");
		
		form.setTags(new HashSet<>(Arrays.asList(tagForm1, tagForm2, tagForm3)));		
		
		JsonNode json = post("/community")
				.json(form)
				.expectedStatus(HttpStatus.CREATED)
				.getJson();
		
		jsonAsserter(json)
			.assertEquals("$.name", "Vídeo Games")
			.assertEquals("$.description", "Comunidade de vídeo games")
			.assertNotNull("$.image")
			.assertNotNull("$.cover")
			.assertThat("$.tags.[*]", hasSize(3))
			.assertThat("$.tags.[*].id", hasItems(tag1.getId().intValue(), tag2.getId().intValue()))
			.assertThat("$.tags.[*].name", hasItems(tag1.getName(), tag2.getName(), "4Players"));
	}
	
	@Test
	public void testDelete() throws Exception{

		User marcos = user("marcos.fernandes").build();
		saveAll(marcos);
		
		Community games = community(marcos, "Games")
				.description("Comunidade de games")
				.image("imagem").build();
		
		saveAll(games);
		
		login(marcos);
		
		delete("/community/%s", games.getId())
			.expectedStatus(HttpStatus.OK);
	}
	
	@Test
	public void testAutocompleteTag() throws Exception {
		
		User lucas = user("lucas.martins").build();
		
		Tag tag1 = tag("Books").build();
		Tag tag2 = tag("Movie").build();
		Tag tag3 = tag("Movies & Series").build();
		Tag tag4 = tag("Cinema").build();
		
		saveAll(lucas, tag1, tag2, tag3, tag4);
		login(lucas);
		
		jsonAsserter(get("community/tag/autocomplete").queryParam("attr", "Mov").getJson())
			.assertThat("$", hasSize(2))
			.assertThat("$.[*].id", contains(tag2.getId().intValue(), tag3.getId().intValue()))
			.assertThat("$.[*].name", contains("Movie", "Movies & Series"));
	}
	
}
