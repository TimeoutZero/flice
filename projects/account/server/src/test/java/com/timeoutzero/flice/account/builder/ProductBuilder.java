package com.timeoutzero.flice.account.builder;

import aleph.AbstractBuilder;

import java.util.UUID;

import com.timeoutzero.flice.account.entity.Product;

public class ProductBuilder extends AbstractBuilder<Product> {

	public static ProductBuilder product(String name) {
		return new ProductBuilder().name(name).token(UUID.randomUUID().toString());
	}
	
	public ProductBuilder name(String name) {
		return set("name", name);
	}
	
	public ProductBuilder token(String token) {
		return set("token", token);
	}
}
