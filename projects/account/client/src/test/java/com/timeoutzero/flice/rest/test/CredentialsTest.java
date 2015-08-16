package com.timeoutzero.flice.rest.test;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.timeoutzero.flice.rest.Credentials;

@RunWith(JUnit4.class)
public class CredentialsTest {

	@Test
	public void shouldGenerateUrlWithCredentials() {
		
		String url 		 = "http://www.flice.io/api";
		String token 	 = UUID.randomUUID().toString();
		Credentials credentials = new Credentials(url, token);
		
		String expected = String.format("http://www.flice.io/api?token=%s", token);
		String actual   = credentials.getUrl("");
		
		Assert.assertEquals(expected, actual);
		
	}
}
