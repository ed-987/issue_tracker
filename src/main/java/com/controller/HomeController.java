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

import com.model.User;
import com.security.SecurityTools;
import com.repository.UserRepository;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/")
    //public String home(Model model
    //		, @AuthenticationPrincipal OidcUser principal
    //		) {
    public String home(Model model, @AuthenticationPrincipal OAuth2User principal) {	
        //if (principal != null) {
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        	//User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("username", auth.getPrincipal());
            model.addAttribute("auth", auth.toString());
        	//model.addAttribute("profile", principal.getClaims());
            model.addAttribute("profile", principal);
            //model.addAttribute("auth2", principal.getAttributes().toString());

            
        //}
        return "index";
    }
    
    @GetMapping("/admin")
    public String test(@AuthenticationPrincipal OAuth2User principal,HttpServletResponse response) throws IOException{
   
    	SecurityTools.adminAuth(principal.getAttribute("http://role/").toString());   
        return "admin";
        
    }
	

    @GetMapping("/user")
    @ResponseBody
    public Map<String, String> getUserInfo(@AuthenticationPrincipal OAuth2User user) {

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("email", user.getAttribute("email"));
        userInfo.put("id",    user.getAttribute("sub"));

        return userInfo;
    }
    
    @GetMapping(path="/db")
    public @ResponseBody List<User> getAllUsers() {
      // This returns a JSON or XML with the users
      userRepository.save(new User("test","test@x.com","nick2"));
      
      logger.debug("output: {}",userRepository.findAll());
      return userRepository.findAll();
    }

}