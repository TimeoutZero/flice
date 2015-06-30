package com.timeoutzero.flice.account.test;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import aleph.AbstractBuilder;
import aleph.PersistenceProvider;

public class JpaPersistenceProvider implements PersistenceProvider {
	
	private List<String> commands = new ArrayList<String>();
	private List<String> nativeQuery = new ArrayList<String>();

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private JdbcTemplate template;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Override
	public void save(final List<AbstractBuilder<?>> builders) {
		
		TransactionTemplate template = new TransactionTemplate(transactionManager);

		template.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				for (AbstractBuilder<?> b : builders) {
					b.build();
					em.persist(b.get());
				}
				em.flush();
				em.clear();
				em.close();
			}
		});

	}

	public JpaPersistenceProvider addQuery(String query) {
		this.commands.add(query);
		return this;
	}

	public JpaPersistenceProvider addNativeQuery(String query) {
		addQuery(query);
		this.nativeQuery.add(query);
		return this;
	}
	protected TestRestTemplate template() {
		return new TestRestTemplate();
	}

	@Override
	public void clear() {

		//this.template.execute("TRUNCATE SCHEMA public AND COMMIT");
	}
}