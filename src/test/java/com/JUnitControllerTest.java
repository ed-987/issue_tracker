package com;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.controller.HomePageController;
import com.security.SecurityConfig;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers =  HomePageController.class)
@ContextConfiguration(classes=SecurityConfig.class)
public class JUnitControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	SecurityConfig s;

	
	@MockBean
	HttpSecurity h;
	
//    @Test
//    public void testHomeController() throws Exception {
//    	RequestBuilder request = MockMvcRequestBuilders.get("/test");
//    	MvcResult result = mvc.perform(request).andReturn();
//        //HomeController homeController = new HomeController();
//        //String result = homeController.test();
//        assertEquals("test",result.getResponse().getContentAsString());
//    }
//    
    @Test
    public void test2() throws Exception {
    	mvc.perform(get("/homepage")).andExpect(status().isOk());
    	//mvc.perform(get("/homepage")).andExpect(content().string("issue tracker test"));
    }

}
