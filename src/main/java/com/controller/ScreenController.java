package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service.ScreenService;
import com.service.UserService;

@Controller
public class ScreenController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	UserService userService;
    
    @GetMapping("/screen")
    @ResponseBody
    public String screen(@RequestParam(required = false) Boolean dark_mode) {
    
    	ScreenService.dark_mode=dark_mode;
    	userService.updateUserDarkMode(ScreenService.logged_in_user, dark_mode);
    	return null;
            
    }
    
    @GetMapping("/columns")
    @ResponseBody
    public String columns(@RequestParam(required = false) Boolean created,
    		              @RequestParam(required = false) Boolean status,
    		              @RequestParam(required = false) Boolean updated,
    		              @RequestParam(required = false) Boolean title) {
    
    	ScreenService.columns.put("created", created);
    	ScreenService.columns.put("status", status);
    	ScreenService.columns.put("updated", updated);
    	ScreenService.columns.put("title", title);
    	userService.updateUserColumns(ScreenService.logged_in_user, ScreenService.columns);
    	//logger.debug("created:{}",created);
    	return null;
            
    }
    

}