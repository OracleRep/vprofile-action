package com.visualpathit.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.visualpathit.account.model.User;

/**
 * Repository for {@link User} persistence operations.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by username.
     *
     * @param username the username
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
}
