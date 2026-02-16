package com.visualpathit.account.service;

import java.util.List;

import com.visualpathit.account.model.User;

/**
 * Service interface for user-related operations.
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
     * @param username the username to search for
     * @return the matching user, or {@code null} if not found
     */
    User findByUsername(String username);

    /**
     * Finds a user by id.
     *
     * @param id the user id
     * @return the matching user, or {@code null} if not found
     */
    User findById(long id);

    /**
     * Returns a list of all users.
     *
     * @return the list of users
     */
    List<User> getList();
}
