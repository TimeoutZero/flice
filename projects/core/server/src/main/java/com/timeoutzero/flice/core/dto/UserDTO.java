package com.timeoutzero.flice.core.dto;

import com.timeoutzero.flice.core.domain.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class UserDTO {

	private Long id;
	private Long accountId;

	public UserDTO(User user) {
		this.id 		= user.getId();
		this.accountId  = user.getAccountId();
	}
}
