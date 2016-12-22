package com.oauth.integration.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oauth.integration.dao.UserDao;
import com.oauth.integration.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	private EntityManager entityManager;
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	@Transactional
	public User createUser(User user) {
		entityManager.persist(user);
		entityManager.flush();
		return user;
	}
	
	@Transactional
	public User updateUser(Long id, String name, String surname, String phone) {
		User user = entityManager.find(User.class, id);
		user.setName(name);
		user.setSurname(surname);
		user.setPhone(phone);
		return user;
	}
	
	@Transactional
	public User getUserById(Long id) {
		return entityManager.find(User.class, id);
	}
	
	@Transactional
	public User getUserByEmail(String email) {
		TypedQuery<User> query = entityManager.createQuery("SELECT e FROM User e where e.email = ?1", User.class);
		query.setParameter(1, email);
		
		List<User> users = query.getResultList();
		
		if(users.size()>0)
			return users.get(0);
		else
			return null;
	}

	@Transactional
	public User getUserByUsernameOrEmail(String param) {
		TypedQuery<User> query = entityManager.createQuery("SELECT e FROM User e where e.username = ?1 or e.email = ?2", User.class);
		query.setParameter(1, param);
		query.setParameter(2, param);
		
		List<User> users = query.getResultList();
		
		if(users.size()>0)
			return users.get(0);
		else
			return null;
	}
	
	@Transactional
	public List<User> listUsers(int offset, int limit) {
		TypedQuery<User> query = entityManager.createQuery("SELECT e FROM User e", User.class);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		return query.getResultList();
	}
	
	@Transactional
	public void activateUser(User user) {
		user.setActivated(true);
	}
	
	@Transactional
	public void deactivateUser(User user) {
		user.setActivated(false);
	}
	
}
