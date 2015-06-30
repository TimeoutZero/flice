package com.timeoutzero.flice.core.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@EqualsAndHashCode()
@ToString()
@Builder
public class Topic{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="name")
	private String name;
	 
	@ManyToOne 
	private Community community;
	
	@ManyToOne
	private User owner;
	
	@Column(name="created")
	private LocalDateTime created;
	
	@Column(name="active")
	private Boolean active;
	
}
