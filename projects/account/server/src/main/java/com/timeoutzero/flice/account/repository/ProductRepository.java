package com.timeoutzero.flice.account.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.timeoutzero.flice.account.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

	@Query("SELECT WHEN count(p) > 0 true ELSE false END FROM Product p WHERE p.token = :token")
	boolean existByToken(@Param("token") String token);
	
	Product findByToken(String token);
}
