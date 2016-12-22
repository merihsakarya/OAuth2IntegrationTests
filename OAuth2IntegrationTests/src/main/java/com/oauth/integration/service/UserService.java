package com.oauth.integration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oauth.integration.dao.UserDao;
import com.oauth.integration.entity.User;

@Service
public class UserService {

	@Autowired
	UserDao userDao;
	
	public User createUser(User user) {
		return userDao.createUser(user);
	}
	
	public User updateUser(Long id, String name, String surname, String phone) {
		return userDao.updateUser(id, name, surname, phone);
	}

	public User getUserById(Long id) {
		return userDao.getUserById(id);
	}
	
	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}
	
	public User getUserByUsernameOrEmail(String email) {
		return userDao.getUserByUsernameOrEmail(email);
	}
	
	public List<User> listUsers(int offset, int limit) {
		return userDao.listUsers(offset, limit);
	}
	
	public void activateUser(User user) {
		userDao.activateUser(user);
	}
	
	public void deactivateUser(User user){
		userDao.deactivateUser(user);
	}
	
}
