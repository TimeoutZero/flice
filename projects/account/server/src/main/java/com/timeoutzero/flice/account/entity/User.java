package com.timeoutzero.flice.account.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter @Setter
@NoArgsConstructor
@Table(name = "user")
public class User extends AbstractEntity {

	@Column(name = "user_email", unique = true)
	private String email;
	
	@Column(name =  "user_password")
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Profile profile = new Profile();

}
