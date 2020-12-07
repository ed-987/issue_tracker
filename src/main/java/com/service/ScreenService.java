package com.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ScreenService {
	
	@Autowired 
	UserService userService;
	
	public static boolean dark_mode = false;
	@SuppressWarnings("serial")
	public static HashMap<String, Boolean> columns = new HashMap<String, Boolean>() {{
		put("created", true);
		put("status", true);
	}};
	
	public static boolean tickets_screen_top = false;
	public static boolean admin_screen_top = false;
	
	public static String logged_in_user;
	public static Boolean new_login = false;
	public static String user_email;
	public static Boolean user_admin=false;
	public static List<String> users;
	
	public Boolean check_login(OAuth2User principal) throws JsonMappingException, JsonProcessingException {
		if(new_login) {
			new_login=false;
	    	if(principal != null) {
	    		user_email = principal.getAttribute("email");
	            String roles = principal.getAttribute("http://role/").toString();
	         	@SuppressWarnings("unchecked")
	    		List<String> result = new ObjectMapper().readValue(roles, List.class);
	        	user_admin= result.contains("ADMIN");
	        	userService.updateUserEmail(logged_in_user, user_email);
	        	users=userService.getUsers();
	        	return true;
	    	}else {
	    		return false;
	    	}
		}else {
			return true;
		}
	}
	
}
