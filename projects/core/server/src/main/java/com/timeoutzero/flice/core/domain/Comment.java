package com.timeoutzero.flice.core.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@EqualsAndHashCode()
@ToString()
@Builder
@Table(name = "comment")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	@Lob
	@Column(name = "comment_content")
	private String content;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "comment_created")
	private DateTime created = DateTime.now();
	
	@ManyToOne
	@JoinColumn(name = "topic_id", foreignKey = @ForeignKey(name = "FK_TOPIC_COMMENT"))
	private Topic topic;
	
	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_COMMENT_OWNER"))
	private User owner;
}
