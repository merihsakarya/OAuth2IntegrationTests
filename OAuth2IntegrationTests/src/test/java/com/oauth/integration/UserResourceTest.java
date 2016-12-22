package com.oauth.integration;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import com.oauth.integration.dao.UserDao;
import com.oauth.integration.entity.User;
import com.oauth.integration.enums.RoleEnum;
import com.oauth.integration.util.OAuthHelper;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes=Application.class)
public class UserResourceTest {
	
	@Autowired
	WebApplicationContext context;
	
	@Autowired
	private FilterChainProxy springSecurityFilterChain;
    
    private MockMvc mvc;

    @Autowired
    private OAuthHelper authHelper;
   
    @Autowired
    private UserDao userDao;
   
    private static User defUser;

    @Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context)
				.addFilter(springSecurityFilterChain).build();

		if(defUser == null){			
			defUser = userDao.getUserById(1L);
		}
	}
    
    @Test
    public void testEchoAnonymous() throws Exception {
        ResultActions resultActions = mvc.perform(get("/user/echo")).andDo(print());

        resultActions
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testEchoAuthenticated() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_USER.name());
        ResultActions resultActions = mvc.perform(get("/user/echo").with(bearerToken)).andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string("echo"));
    }

    @Test
    public void getUserByIdSuccesfull() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_USER.name());
        ResultActions resultActions = mvc.perform(get("/user/id/" + defUser.getId())
        		.with(bearerToken)).andDo(print());
        resultActions
		        .andExpect(status().isOk())
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.username", is(defUser.getUsername())))
				.andExpect(jsonPath("$.email", is(defUser.getEmail())))
				.andExpect(jsonPath("$.name", is(defUser.getName())))
				.andExpect(jsonPath("$.surname", is(defUser.getSurname())))
				.andExpect(jsonPath("$.phone", is(defUser.getPhone())));			
    }
    
    @Test
    public void getUserByIdCouldNotFound() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_USER.name());
        ResultActions resultActions = mvc.perform(get("/user/id/100")
        		.with(bearerToken)).andDo(print());
        
        resultActions
                .andExpect(status().isOk());
        
        Assert.isTrue(resultActions.andReturn().getResponse().getContentAsString().equals(""));
    }
    
    @Test
    public void getUserByEmailSuccesfull() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_USER.name());
        ResultActions resultActions = mvc.perform(post("/user/email")
        		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
        		.param("email", defUser.getEmail())
        		.with(bearerToken)).andDo(print());
        resultActions
		        .andExpect(status().isOk())
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.username", is(defUser.getUsername())))
				.andExpect(jsonPath("$.email", is(defUser.getEmail())))
				.andExpect(jsonPath("$.name", is(defUser.getName())))
				.andExpect(jsonPath("$.surname", is(defUser.getSurname())))
				.andExpect(jsonPath("$.phone", is(defUser.getPhone())));
    }
    
    @Test
    public void updateUserSuccessfull() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_USER.name());

        Map<String,String> updateParams = new HashMap<String,String>();
        updateParams.put("name", 	"updatedName");
        updateParams.put("surname", "updatedSurname");
        updateParams.put("phone", 	"+44700000000");
        
		ResultActions resultActions = mvc.perform(put("/user/update")
        		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
        		.param("id", 		defUser.getId().toString())
        		.param("name", 		updateParams.get("name"))
        		.param("surname", 	updateParams.get("surname"))
        		.param("phone", 	updateParams.get("phone"))
        		.with(bearerToken)).andDo(print());
        resultActions
		        .andExpect(status().isOk())
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.username", 	is(defUser.getUsername())))
				.andExpect(jsonPath("$.email", 		is(defUser.getEmail())))
				.andExpect(jsonPath("$.name", 		is(updateParams.get("name"))))
				.andExpect(jsonPath("$.surname", 	is(updateParams.get("surname"))))
				.andExpect(jsonPath("$.phone", 		is(updateParams.get("phone"))));
    }

}