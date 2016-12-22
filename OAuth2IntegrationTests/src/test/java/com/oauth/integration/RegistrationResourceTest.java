package com.oauth.integration;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.oauth.integration.entity.User;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes=Application.class)
public class RegistrationResourceTest {
    
    @Autowired
    WebApplicationContext context;
    
    @Autowired
    private FilterChainProxy springSecurityFilterChain;
    
    private MockMvc mvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(springSecurityFilterChain).build();
    }
    
    @Test
    public void testEchoAnonymous() throws Exception {
        ResultActions resultActions = mvc.perform(get("/register/echo")).andDo(print());

        resultActions
            .andExpect(status().isOk())
            .andExpect(content().string("echo"));
    }

    @Test
    public void createUserSuccessfull() throws Exception {
        
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("123456");
        user.setEmail("testuser@domain.com");
        user.setName("TestUser");
        user.setSurname("TestSurname");
        user.setPhone("00902124780371");
        
        ResultActions resultActions = mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username",  user.getUsername())
                .param("password",  user.getPassword())
                .param("email",     user.getEmail())
                .param("name",      user.getName())
                .param("surname",   user.getSurname())
                .param("phone",     user.getPhone()))
                .andDo(print());
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.surname", is(user.getSurname())))
                .andExpect(jsonPath("$.phone", is(user.getPhone())));
    }

}