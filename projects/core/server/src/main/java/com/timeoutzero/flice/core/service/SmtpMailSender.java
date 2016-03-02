package com.timeoutzero.flice.core.service;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SmtpMailSender {
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpMailSender.class);

	@Autowired
	private JavaMailSender javaMailSender;

	public void send(String to, String subject, String path, Map<String, String> params) {
		
		try {
			
			byte[] bytes = IOUtils.toByteArray(getClass().getResourceAsStream(path));
			String body = new String(bytes);
			
			for (Entry<String, String> entry : params.entrySet()) {
				body = body.replace(entry.getKey(), entry.getValue());
			}
			
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject(subject);
			helper.setTo(to);
			helper.setText(body, true);
			
			javaMailSender.send(message);
		
		} catch (MessagingException e) {
			LOG.error("Problem to invite e-mail: [ {} ] , details : {}", to,  e.getLocalizedMessage());
		} catch (IOException e) {
			LOG.error("Problem to find e-mail template details : {}", e.getLocalizedMessage());
		}

	}
}
