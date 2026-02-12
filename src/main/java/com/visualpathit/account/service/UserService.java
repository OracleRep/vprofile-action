package com.visualpathit.account.service;

import java.util.List;

import com.visualpathit.account.model.User;

/**
 * Service interface for user operations.
 */
public interface UserService {

    /**
     * Saves a user.
     *
     * @param user the user to save
     */
    void save(User user);

    /**
     * Finds a user by username.
     *
     * @param username the username
     * @return the user if found
     */
    User findByUsername(String username);

    /**
     * Finds a user by id.
     *
     * @param id the user id
     * @return the user if found
     */
    User findById(long id);

    /**
     * Returns all users.
     *
     * @return list of users
     */
    List<User> getList();
}
