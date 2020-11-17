package com.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Activity;
import com.security.ForbiddenException;
import com.security.SecurityTools;
import com.service.ScreenService;
import com.service.TicketService;

@Controller
public class HomeController {

	@Autowired
	private TicketService ticketService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OAuth2User principal) throws JsonMappingException, JsonProcessingException {
    	model.addAttribute("admin",false);
    	if(principal != null) {
    		String user = principal.getAttribute("email");
    		model.addAttribute("user",user);
    		model.addAttribute("dashboard",ticketService.getDashboard(user));
            String roles = principal.getAttribute("http://role/").toString();
         	@SuppressWarnings("unchecked")
    		List<String> result = new ObjectMapper().readValue(roles, List.class);
        	if(result.contains("ADMIN")) {
        		model.addAttribute("admin",true);
        	}
    	}
		model.addAttribute("dark_mode",ScreenService.dark_mode);

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