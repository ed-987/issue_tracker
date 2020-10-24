package com.mock_auth;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.security.Auth2Token;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class MockMvcOAuth2Test {

    @Autowired
    private MockMvc mockMvc;
    
    private ResultActions result;
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    public void givenUserIsOnPage(String user, String page) throws Exception {
        OAuth2AuthenticationToken principal = Auth2Token.buildPrincipal(user);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            new SecurityContextImpl(principal));
        result = mockMvc.perform(MockMvcRequestBuilders.get(page)
                .session(session)); 
    }
    
    @Test
    public void adminStatusIsOkOnAdminPage() throws Exception {
    	givenUserIsOnPage("ADMIN","/admin");
    	result.andExpect(status().isOk());
    }
    
    @Test
    public void userStatusIsForbiddenOnAdminPage() throws Exception {
    	givenUserIsOnPage("","/admin");
    	result.andExpect(status().is4xxClientError());
    }
    
    @Test
    public void userStatusIsOkOnHomePage() throws Exception {
    	givenUserIsOnPage("","/");
    	result.andExpect(status().isOk());
    }


//        	  .andDo(print())
//            .andExpect(status().isOk())
//            .andReturn().getResponse().getContentAsString();
//        
//        logger.info("result from API: {}",result);
//        
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.email").value(email))
//            .andExpect(jsonPath("$.id").value(id));

}
