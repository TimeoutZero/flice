package com.timeoutzero.flice.account.entity;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Profile extends AbstractEntity {

	private String name;
	private String username;
	private String photo;
	
}
