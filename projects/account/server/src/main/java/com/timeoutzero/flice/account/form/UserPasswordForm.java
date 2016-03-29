package com.timeoutzero.flice.account.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordForm {

	private String actualPassword;
	private String newPassword;
	private String newPasswordConfirmation;
}
