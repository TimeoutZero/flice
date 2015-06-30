package com.timeoutzero.flice.core.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.joda.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude="tags")
@ToString()
@Builder
public class Community{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description; 
	
	@ManyToOne
	private User owner;
	
	@Column(name="created")
	private LocalDateTime created;
	
	@Column(name="image")
	private String image;

	@Column(name="active")
	private Boolean active;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "CommunityTag", joinColumns={@JoinColumn(name="comunity_id")}, inverseJoinColumns={@JoinColumn(name="tag_id")})
	private List<Tag> tags = new ArrayList<Tag>();
	
}
