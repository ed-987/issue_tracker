package com.security;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SecurityTools {
	
	public static void adminAuth(String roles) throws JsonMappingException, JsonProcessingException {
     	@SuppressWarnings("unchecked")
		List<String> result = new ObjectMapper().readValue(roles, List.class);
    	if(!result.contains("ADMIN")) {throw new ForbiddenException();}
	}

}
