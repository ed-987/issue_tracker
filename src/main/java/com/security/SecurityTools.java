package com.security;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SecurityTools {
	
	public static void adminAuth(String principal) throws JsonMappingException, JsonProcessingException {
    	String roles = principal;
    	@SuppressWarnings("unchecked")
		List<String> result = new ObjectMapper().readValue(roles, List.class);
    	if(!result.contains("ADMIN")) {throw new ForbiddenException();}
	}

}
