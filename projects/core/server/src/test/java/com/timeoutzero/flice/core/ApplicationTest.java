package com.timeoutzero.flice.core;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonassert.JsonAssert;
import com.jayway.jsonassert.JsonAsserter;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.enums.Role;
import com.timeoutzero.flice.core.security.AuthenticatorService;
import com.timeoutzero.flice.core.security.TokenFilter;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { CoreApplication.class })
@IntegrationTest({ "server.port=10001", "spring.profiles.active=DEV", "mock.s3=teste", "aws.access.token=teste", "aws.secret.key=teste"} )
public abstract class ApplicationTest {

	@Autowired
	private JpaTransactionManager manager;

	@Autowired
	private JdbcTemplate template;

	private static List<Object> toPersist = new ArrayList<Object>();
	private final String server;

	@Mock
	private AuthenticatorService authenticatorService;
	
	@Autowired
	@InjectMocks
	private TokenFilter tokenFilter;
	
	private static final String token = "teste";

	public ApplicationTest() {
		this.server = "http://localhost:10001/";
	}

	@Before
	public void setUp() {
		
		FileUtils.deleteQuietly(new File("/tmp/community"));
		MockitoAnnotations.initMocks(this);

		toPersist.clear();
		template.execute("TRUNCATE SCHEMA public AND COMMIT");
	}

	protected void add(Object... objects) {
		for (Object object : objects) {
			toPersist.add(object);
		}
	}

	protected void saveAll(Object... objects) {
		this.add(objects);
		this.saveAll();
	}

	protected void saveAll() {
		EntityManager em = manager.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		for (Object object : toPersist) {
			em.persist(object);
		}
		em.flush();
		em.clear();
		em.close();
		toPersist.clear();
		em.getTransaction().commit();
	}

	protected void login(User user) {
		
		List<SimpleGrantedAuthority> roles = user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		Mockito.when(authenticatorService.createAuthentication("teste")).thenReturn(new UsernamePasswordAuthenticationToken(user, token, roles));
	}
	
	protected void anonymous() {
		Mockito.when(authenticatorService.createAuthentication("teste")).thenReturn(new UsernamePasswordAuthenticationToken(null, null, Arrays.asList(new SimpleGrantedAuthority(Role.ANONYMOUS))));
	}

	protected RequestBuilder get(String uri) {
		return new RequestBuilder(server, uri, token, HttpMethod.GET);
	}

	protected RequestBuilder put(String uri) {
		return new RequestBuilder(server, uri, token, HttpMethod.PUT);
	}

	protected RequestBuilder post(String uri) {
		return new RequestBuilder(server, uri, token, HttpMethod.POST);
	}

	protected RequestBuilder delete(String uri) {
		return new RequestBuilder(server, uri, token, HttpMethod.DELETE);
	}

	// With path variables
	protected RequestBuilder get(String uri, Object... path) {
		return get(String.format(uri, path));
	}

	protected RequestBuilder post(String uri, Object... path) {
		return post(String.format(uri, path));
	}

	protected RequestBuilder put(String uri, Object... path) {
		return put(String.format(uri, path));
	}

	protected RequestBuilder delete(String uri, Object... path) {
		return delete(String.format(uri, path));
	}

	protected TestRestTemplate template() {
		return new TestRestTemplate();
	}

	protected JsonAsserter jsonAsserter(JsonNode json) throws UnsupportedEncodingException {
		return JsonAssert.with(json.toString());
	}
}
