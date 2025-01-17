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
import com.service.TicketService;
import com.service.UserService;

@Component
public class LoginListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {
	
	@Autowired
	UserService userService;
	
	@Autowired 
	TicketService ticketService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event)
    {
        //UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        // ...
    	ScreenService.new_login=true;
    	ScreenService.logged_in_user=event.getAuthentication().getName();
    	List<User> found_user = userService.getUser(ScreenService.logged_in_user);
    	if(found_user.size()>0) {  		
    		ScreenService.dark_mode=found_user.get(0).getDark_mode();
    		ScreenService.columns = (found_user.get(0).getColumns() != null) ? found_user.get(0).getColumns() : ScreenService.columns;
     	}else {
    		ScreenService.dark_mode=false;
    		userService.saveUser(new User(ScreenService.logged_in_user, ScreenService.dark_mode, ScreenService.columns)); 		
    	}
    	logger.debug("x123: {}",ScreenService.logged_in_user);
    }

}
