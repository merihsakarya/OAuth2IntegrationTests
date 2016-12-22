package com.oauth.integration.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oauth.integration.entity.User;
import com.oauth.integration.service.UserService;

@RestController
@RequestMapping("/user")
class UserResource {
	
	@Autowired
	UserService userService;

	@RequestMapping(value = "/echo", method = RequestMethod.GET)
	public String echo() {
		return "echo";
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		
		User user = userService.getUserById(id);		
		return new ResponseEntity<User>(user, HttpStatus.OK);		
	}

	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public ResponseEntity<User> getUserByEmail(@RequestParam String email) {	
		
		User user = userService.getUserByEmail(email);	
		return new ResponseEntity<User>(user, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
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
		user.setName(surname);
		user.setSurname(surname);
		user = userService.createUser(user);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(
			@RequestParam("id") 		Long id,
			@RequestParam("name") 		String name,
			@RequestParam("surname") 	String surname,
			@RequestParam("phone") 		String phone) {
		
		User result = userService.updateUser(id, name, surname, phone);
		return new ResponseEntity<User>(result, HttpStatus.OK);
	}
	
}
