package com.oauth.integration.dao;

import java.util.List;

import com.oauth.integration.entity.User;

public interface UserDao {
    
    /*
     * Create/Update User
     */
    public User createUser(User user);
    public User updateUser(Long id, String name, String surname, String phone);
    
    /*
     * Get User
     */
    public User getUserById(Long id);
    public User getUserByEmail(String email);
    public User getUserByUsernameOrEmail(String param);
    
    /*
     * List Users
     */
    public List<User> listUsers(int offset, int limit);
    
    /*
     * Activate/Deactivate User
     */
    public void activateUser(User user);
    public void deactivateUser(User user);

}
