package com.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.service.ScreenService;
import com.service.TicketService;

@Controller
public class HomeController {

	@Autowired
	private ScreenService screenService;
	
	@Autowired
	private TicketService ticketService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OAuth2User principal ) throws JsonMappingException, JsonProcessingException {
    	ScreenService.tickets_screen_top=true;
    	ScreenService.admin_screen_top=true;   	
    	
    	if(screenService.check_login(principal)) {
    		model.addAttribute("user",ScreenService.user_email);
    		model.addAttribute("dashboard",ticketService.getDashboard(ScreenService.user_email));
        	model.addAttribute("admin",ScreenService.user_admin);
    	}    	    	
		model.addAttribute("dark_mode",ScreenService.dark_mode);
		model.addAttribute("columns",ScreenService.columns);

        return "index";
    }

    @GetMapping("/user")
    @ResponseBody
    public Map<String, String> getUserInfo(@AuthenticationPrincipal OAuth2User user) {

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("email", user.getAttribute("email"));
        userInfo.put("id",    user.getAttribute("sub"));

        return userInfo;
    }
    

}