package com.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.User;

@Service
public class ScreenService {
	
	@Autowired 
	UserService userService;
	
	public static boolean dark_mode = false;
	@SuppressWarnings("serial")
	public static HashMap<String, Boolean> columns = new HashMap<String, Boolean>() {{
		put("created", true);
		put("status", true);
		put("updated", false);
		put("title", true);
	}};
	
	public static boolean tickets_screen_top = false;
	public static boolean admin_screen_top = false;
	
	public static boolean restarted = true;
	
	public static String logged_in_user;
	public static Boolean new_login = false;
	public static String user_email;
	public static Boolean user_admin=false;
	public static List<String> users;
	
	public Boolean check_login(OAuth2User principal) throws JsonMappingException, JsonProcessingException {	    				
		if(principal != null) {
			user_email = principal.getAttribute("email");
			String roles = principal.getAttribute("http://role/").toString();
			@SuppressWarnings("unchecked")
			List<String> result = new ObjectMapper().readValue(roles, List.class);
			user_admin= result.contains("ADMIN");
			if(new_login) {
				new_login=false;
				userService.updateUserEmail(logged_in_user, user_email);
			}			
			if(restarted) {
				restarted = false;
				logged_in_user = principal.getName();
		    	List<User> found_user = userService.getUser(logged_in_user);
		    	if(found_user.size()>0) {   		
		    		dark_mode=found_user.get(0).getDark_mode();
		    		columns = (found_user.get(0).getColumns() != null) ? found_user.get(0).getColumns() : columns;
		    	}else {
		    		dark_mode=false;
		    		userService.saveUser(new User(logged_in_user, dark_mode, columns)); 	
		    		userService.updateUserEmail(logged_in_user, user_email);
		    	}
			}
			users=userService.getUsers();
			return true;
		}else {
			return false;
		}
	}
	
}
