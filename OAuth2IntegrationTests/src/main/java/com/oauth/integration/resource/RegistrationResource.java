package com.oauth.integration.resource;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oauth.integration.entity.User;
import com.oauth.integration.service.UserService;

@RestController
class RegistrationResource {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/register/echo", method = RequestMethod.GET)
	public String echo() {
        return "echo";
    }
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(
			@RequestParam("username") 	String username,
			@RequestParam("password") 	String password,
			@RequestParam("email") 		String email,
			@RequestParam("name") 		String name,
			@RequestParam("surname") 	String surname,
			@RequestParam("phone") 		String phone) {	
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setName(name);
		user.setSurname(surname);
		user.setPhone(phone);
		user.setRegistrationDate(new Date());
        user.setActivated(true);
		user = userService.createUser(user);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
