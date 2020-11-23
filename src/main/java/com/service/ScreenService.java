package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScreenService {
	
	@Autowired 
	UserService userService;
	
	public static boolean dark_mode = false;
	
	public static String logged_in_user;

}
