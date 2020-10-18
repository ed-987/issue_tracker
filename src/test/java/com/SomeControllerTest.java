package com;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.controller.HomeController;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HomeController.class)
public class SomeControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
      mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .defaultRequest(get("/")
        .with(user("user").password("password").roles("USER")))
        .apply(springSecurity())
        .build();
    }


    @Test
    public void test1() {
                  mockMvc.perform(get(...)).andExpect(...);
    }
}
