package com.timeoutzero.flice.account.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User extends AbstractEntity {

	@Column(name = "email", unique = true)
	private String email;
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Profile profile;

}
