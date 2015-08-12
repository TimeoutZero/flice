package com.timeoutzero.flice.account.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
public class User extends AbstractEntity {

	@Column(unique = true)
	private String email;
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Profile profile = new Profile();

}
