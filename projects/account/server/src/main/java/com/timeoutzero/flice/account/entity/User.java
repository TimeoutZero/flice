package com.timeoutzero.flice.account.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends AbstractEntity {

	@Column(name = "user_email", unique = true)
	private String email;
	
	@Column(name =  "user_password")
	private String password;
		
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Profile profile = new Profile();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "last_update")
	private DateTime lastUpdate;

}
