package com.mock_auth;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class MockMvcOAuth2Test {

    @Autowired
    private MockMvc mockMvc;
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private static final String id = "auth0|5f8733ba8c86bc00757e7bc9";
    private static final String email = "e10002001-0@yahoo.com";
	 

    @Test
    public void testGetAuthenticationInfo() throws Exception {
        OAuth2AuthenticationToken principal = buildPrincipal();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            new SecurityContextImpl(principal));

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/admin")
            .session(session))
        	//.andDo(print())
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        
        logger.info("result from API: {}",result);
        
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")
//                .session(session))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.email").value(email))
//            .andExpect(jsonPath("$.id").value(id));
    }

    private static OAuth2AuthenticationToken buildPrincipal() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", id);
        attributes.put("email", email);
        attributes.put("http://role/", "[\"ADMIN\"]");

        List<GrantedAuthority> authorities = Collections.singletonList(
                new OAuth2UserAuthority("ROLE_USER", attributes));
        OAuth2User user = new DefaultOAuth2User(authorities, attributes, "sub");
        return new OAuth2AuthenticationToken(user, authorities, "whatever");
    }
}
