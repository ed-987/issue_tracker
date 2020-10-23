package com.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.marketing.newsletter.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.controller.HomeController;
import com.controller.HomePageController;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class AuthTest {

    @Autowired
    private MockMvc mvc;
    
    @Autowired
    public SecurityConfig securityConfig;
    
    private ResultActions result;
    
    private void given_UserIsOnHomePage() throws Exception {
    	result = mvc.perform(
    			get("/homepage")
    			.accept(MediaType.TEXT_HTML));
    }
    
    @Test
    public void testHomePageWorking() throws Exception{
    	given_UserIsOnHomePage();

    }
    
}
