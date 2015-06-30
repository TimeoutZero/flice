package com.timeoutzero.flice.account.repository;

import org.springframework.data.repository.CrudRepository;

import com.timeoutzero.flice.account.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
