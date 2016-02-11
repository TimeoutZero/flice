package com.timeoutzero.flice.core.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.timeoutzero.flice.rest.dto.ProfileDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "roles", "communitys" } )
@ToString(exclude = { "roles", "communitys" } )
@Builder
@Table(name = "user")
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;
	
	@Column(name = "user_account_id")
	private Long accountId;
	
	@Column(name = "user_email")
	private String email;
	
	@Column(name = "invites")
	private int invites;

	@Transient
	private ProfileDTO profile = new ProfileDTO();
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "user_roles", joinColumns = { @JoinColumn(name = "user_id") }, foreignKey = @ForeignKey(name = "FK_USER_ROLES"))
	private List<String> roles = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_communities", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "community_id") })
	private List<Community> communitys = new ArrayList<>();
	

}
