package com.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.model.User;
import com.service.ScreenService;
import com.service.UserService;

@Component
public class LoginListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {
	
	@Autowired
	UserService userService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event)
    {
        //UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        // ...
    	
    	ScreenService.logged_in_user=event.getAuthentication().getName();
    	List<User> found_user = userService.getUser(ScreenService.logged_in_user);
    	if(found_user.size()>0) {
    		ScreenService.dark_mode=found_user.get(0).getDark_mode();
    	}else {
    		userService.saveUser(new User(ScreenService.logged_in_user,false));
    		ScreenService.dark_mode=false;
    	}
    		
    	//logger.debug("x123: {}",event.getAuthentication().getName());
    }

}