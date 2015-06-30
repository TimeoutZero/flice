package com.timeoutzero.flice.core;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonassert.JsonAssert;
import com.jayway.jsonassert.JsonAsserter;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.rest.enums.GrantType;
import com.timeoutzero.flice.rest.operations.AccountOperations;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { CoreApplication.class })
@IntegrationTest("server.port=10001")
public abstract class ApplicationTest {

	private static List<Object> toPersist = new ArrayList<Object>();

	private final String server;

	@Autowired
	private JpaTransactionManager manager;

	@Autowired
	private JdbcTemplate template;
	
	@Autowired
	private AccountOperations accountOperations;

	private String token;

	public ApplicationTest() {
		this.server = "http://localhost:10001";
	}

	@Before
	public void setUp() {
		toPersist.clear();
		template.execute("TRUNCATE SCHEMA public AND COMMIT");
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() throws Exception {
		final PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
		Properties properties = new Properties();

		properties.setProperty("account.url", "http://localhost:9090/account/api");
		properties.setProperty("account.client.id", "1");
		properties.setProperty("account.secret.key", "1");

		pspc.setProperties(properties);
		return pspc;
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
		token = accountOperations.getTokenOperations().create(user.getEmail(), "1", 1l, GrantType.PASSWORD);

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
