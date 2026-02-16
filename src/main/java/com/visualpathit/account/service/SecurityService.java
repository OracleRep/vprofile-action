package com.visualpathit.account.service;

/**
 * Service interface for security-related operations.
 */
public interface SecurityService {

    /**
     * Returns the username of the currently logged-in user.
     *
     * @return the logged-in username, or {@code null} if none
     */
    String findLoggedInUsername();

    /**
     * Performs automatic login for a user.
     *
     * @param username the username
     * @param password the password
     */
    void autologin(String username, String password);
}
