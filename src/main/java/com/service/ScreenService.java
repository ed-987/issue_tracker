package com.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScreenService {
	
	@Autowired 
	UserService userService;
	
	public static boolean dark_mode = false;
	@SuppressWarnings("serial")
	public static HashMap<String, Boolean> columns = new HashMap<String, Boolean>() {{
		put("created", true);
	}};
	
	public static boolean tickets_screen_top = false;
	public static boolean admin_screen_top = false;
	
	public static String logged_in_user;

}
