package com.oauth.integration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.oauth.integration.dao.UserDao;
import com.oauth.integration.entity.User;
import com.oauth.integration.entity.UserRole;
import com.oauth.integration.enums.RoleEnum;

@RunWith(SpringRunner.class)
@DataJpaTest
@EntityScan(basePackages="com.oauth.integration.entity")
@ComponentScan(basePackages="com.oauth.integration.dao") 
@SpringBootTest(classes=Application.class)
public class UserDaoTests {

    @Autowired
    private TestEntityManager entityManager;
  
    @Autowired
    private UserDao userDao;

    @Test
    public void createUserSuccesfull() {    
        User user = new User();
        user.setUsername("testuser-2");
        user.setPassword("123456");
        user.setEmail("testuser-2@domain.com");
        user.setName("Test User - 2");
        user.setSurname("Test Surname - 2");
        user.setPhone("00902124780371");
        user.setRegistrationDate(new Date());
        user.setActivated(true);
        
        List<UserRole> roles = new ArrayList<UserRole>();
        UserRole role = new UserRole();
        role.setRole(RoleEnum.ROLE_USER);
        role.setUser(user);
        roles.add(role);
        user.setRoles(roles);
        
        userDao.createUser(user);

        User result = entityManager.find(User.class, user.getId());
        Assert.isTrue(result.equals(user));
    }
    
    @Test(expected=javax.validation.ConstraintViolationException.class)
    public void createUserNullEmailError() {
        User user = new User();
        user.setUsername("testuser1");
        user.setPassword("123456");

        user.setName("TestUser");
        user.setSurname("TestSurname");
        user.setPhone("00902124780371");
        user.setRegistrationDate(new Date());
        user.setActivated(true);
        userDao.createUser(user);
    } 

    @Test
    public void findUserById() {    
        User result = userDao.getUserById(1L);
        Assert.isTrue(!result.equals(null));
    }
    
    @Test
    public void findErrorUserById() {
        User result = userDao.getUserById(100L);
        Assert.isTrue(result == null);
    }
    
    @Test
    public void findUserByEmail() { 
        User result = userDao.getUserByEmail("testuser@domain.com");
        Assert.isTrue(!result.equals(null));
    }
    
    @Test
    public void findErrorUserByEmail() {
        User result = userDao.getUserByEmail("unknown-user@domain.com");
        Assert.isTrue(result == null);
    }
}