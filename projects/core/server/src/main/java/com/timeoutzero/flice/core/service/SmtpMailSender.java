package com.timeoutzero.flice.core.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SmtpMailSender {

	@Autowired
	private JavaMailSender javaMailSender;

	public void send(String to, String subject, String body) throws MessagingException {
		
		String url = "http://localhost:3000/#/community/user?email=" + Base64.encodeBase64String(to.getBytes());
		body = body.replace("${URL}", url);
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(body, true);

		javaMailSender.send(message);
	}
}
