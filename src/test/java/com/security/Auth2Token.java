package com.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

public class Auth2Token {

	private static final String id = "auth0|5f8733ba8c86bc00757e7bc9";
    private static final String email = "e10002001-0@yahoo.com";
    
	public static OAuth2AuthenticationToken buildPrincipal(String role) {
		
		role = "[\""+role+"\"]";
		
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", id);
        attributes.put("email", email);
        attributes.put("http://role/", role);

        List<GrantedAuthority> authorities = Collections.singletonList(
                new OAuth2UserAuthority("ROLE_USER", attributes));
        OAuth2User user = new DefaultOAuth2User(authorities, attributes, "sub");
        return new OAuth2AuthenticationToken(user, authorities, "whatever");
    }
}
