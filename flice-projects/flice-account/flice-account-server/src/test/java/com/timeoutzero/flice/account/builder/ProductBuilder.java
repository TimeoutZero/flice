package com.timeoutzero.flice.account.builder;

import aleph.AbstractBuilder;

import com.timeoutzero.flice.account.entity.Product;

public class ProductBuilder extends AbstractBuilder<Product> {

	public static ProductBuilder product(String name) {
		return new ProductBuilder().name(name);
	}
	
	public ProductBuilder name(String name) {
		return set("name", name);
	}
}
