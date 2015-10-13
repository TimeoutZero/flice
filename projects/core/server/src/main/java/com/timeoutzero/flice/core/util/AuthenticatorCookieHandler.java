package com.timeoutzero.flice.core.util;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.timeoutzero.flice.core.security.TokenFilter;

@Component
public class AuthenticatorCookieHandler {
	
	private static final int COOKIE_EXPIRE_SECONDS 	= 86400;
	private static final int COOKIE_EXPIRE_DAYS 	= 15; 

	public Cookie retrieveCookie(HttpServletRequest request, String token) {

		Optional<Cookie> optionalCookie = getAuthorizationCookie(request);

		Cookie cookie = null;

		if (optionalCookie.isPresent()) {
			cookie = update(optionalCookie);
		} else {
			cookie = create(token);
		}
		
		return cookie;
	}

	private Cookie create(String token) {
		Cookie cookie = new Cookie(TokenFilter.CUSTOM_COOKIE_X_FLICE_TOKEN, token);
		setDefaultCookie(cookie);
		return cookie;
	}

	private Cookie update(Optional<Cookie> optionalCookie) {
		Cookie cookie = optionalCookie.get();
		setDefaultCookie(cookie);
		return cookie;
	}

	private void setDefaultCookie(Cookie cookie) {
		cookie.setMaxAge(COOKIE_EXPIRE_SECONDS * COOKIE_EXPIRE_DAYS);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
	}

	private Optional<Cookie> getAuthorizationCookie(HttpServletRequest request) {

		Cookie[] cookies = request.getCookies() == null ? new Cookie[] {} : request.getCookies();

		return Arrays.asList(cookies).stream()
				.filter(c -> c.getName().equals(TokenFilter.CUSTOM_COOKIE_X_FLICE_TOKEN))
				.findFirst();
	}
}
