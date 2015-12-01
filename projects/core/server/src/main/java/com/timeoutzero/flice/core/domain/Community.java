package com.timeoutzero.flice.core.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

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
@EqualsAndHashCode(exclude = { "tags", "members" })
@ToString()
@Builder
@Table(name = "community")
public class Community {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "community_id")
	private Long id;

	@Column(name = "community_name")
	private String name;
	
	@Column(name = "community_description")
	private String description; 
	
	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_COMMUNITY_USER_OWNER"))
	private User owner;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "community_created")
	private DateTime created;
	
	@Column(name = "community_image")
	private String image;
	
	@Column(name = "community_cover")
	private String cover;

	@Column(name = "community_active")
	private Boolean active = true;
	
	@Column(name = "community_privacity")
	private Boolean visibility = true;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "community_members",
		uniqueConstraints = @UniqueConstraint(columnNames = { "community_id", "user_id" }),
		joinColumns = { @JoinColumn(name = "community_id")}, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private Collection<User> members = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "community_tags", joinColumns = { @JoinColumn(name = "comunity_id") }, inverseJoinColumns={ @JoinColumn(name = "tag_id") })
	private List<Tag> tags = new ArrayList<>();
	
	
	
}
