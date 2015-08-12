package com.timeoutzero.flice.account.test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.jayway.jsonassert.JsonAssert;
import com.jayway.jsonassert.JsonAsserter;

public class AssertUtil {

	private JsonAsserter jsonAsserter;
	public AssertUtil(String json) {
		jsonAsserter = JsonAssert.with(json);
	}

	public AssertUtil isJsonError() {
		jsonAsserter.assertThat("$", notNullValue())
				.assertThat("$.message", notNullValue());
		return this;
	}

	public AssertUtil contains(String codigoErro) {
		jsonAsserter.assertThat("$.message", equalTo(codigoErro));
		return this;
	}


	public static AssertUtil with(String json) {
		return new AssertUtil(json);
	}
}
