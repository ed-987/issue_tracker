package com.mock_auth;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class MockAuthTest {

   private static final String CLIENT_ID = "fooClientIdPassword";
   private static final String CLIENT_SECRET = "secret";
	
   @Autowired
   private WebApplicationContext wac;

   @Autowired
   private MockMvc mockMvc;


   @Autowired
   private TestRestTemplate restTemplate;

   private final Logger logger = LoggerFactory.getLogger(getClass());


   @Test
   public void test() throws Exception{
 	   
       //String accessToken = obtainAccessToken("dummyUser", "dummyPassword");
	   logger.info("starting my test...");
       String accessToken = obtainAccessToken("e10002001-0@yahoo.com", "Test1234");
       logger.info("access token: {}",accessToken);

		/*
		 * MvcResult result = mockMvc.perform(get("/homepage") .header("Authorization",
		 * "Bearer " + accessToken) .accept(MediaType.APPLICATION_JSON))
		 * .andExpect(status().isOk()) .andReturn();
		 * 
		 * 
		 * String str = result.getResponse().getContentAsString();
		 * assertTrue(str.contains("\"id\":\"2222\""));
		 */
   }

   private String obtainAccessToken(String username, String password) throws Exception {

      MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
      params.add("grant_type", "password");
      params.add("username", username);
      params.add("password", password);
      params.add("scope", "openid");
      params.add("client_id", CLIENT_ID);

      //String base64ClientCredentials = new String(Base64.encodeBase64("5QJWBGDaRFSV1cnmcYC6dcFPKOgBut0v:b3WfnMHBCN7bmVw-Ml9-B4SwQktnkbr14eilPPFJvFmWh2gxi75sHl5bMLPkCTsn".getBytes()));
      //String base64ClientCredentials = "NVFKV0JHRGFSRlNWMWNubWNZQzZkY0ZQS09nQnV0MHY6YjNXZm5NSEJDTjdibVZ3LU1sOS1CNFN3UWt0bmticjE0ZWlsUFBGSnZGbVdoMmd4aTc1c0hsNWJNTFBrQ1Rzbg==";

      ResultActions result
            = mockMvc.perform(post("/oauth/token")
            .params(params)
            .with(httpBasic(CLIENT_ID, CLIENT_SECRET))
            //.header("Authorization","Basic " + base64ClientCredentials)
            .accept("application/json;charset=UTF-8"))
            .andExpect(status().isOk());

      String resultString = result.andReturn().getResponse().getContentAsString();

      JacksonJsonParser jsonParser = new JacksonJsonParser();
      return jsonParser.parseMap(resultString).get("access_token").toString();
   }

}