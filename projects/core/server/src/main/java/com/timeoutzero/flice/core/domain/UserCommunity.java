package com.timeoutzero.flice.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@EqualsAndHashCode()
@ToString()
@Builder
@Table(name = "user_community")
public class UserCommunity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_community_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_COMMUNITY_USER"))
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "community_id", foreignKey = @ForeignKey(name = "FK_USER_COMMUNITY_COMMUNITY"))
	private Community community;

	@Column(name = "user_community_favorite")
	private Boolean favorite;
	
}
