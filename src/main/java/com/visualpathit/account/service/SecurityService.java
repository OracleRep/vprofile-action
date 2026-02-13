package com.visualpathit.account.service;

/**
 * Security helper operations.
 */
public interface SecurityService {

    /**
     * Finds the username of the currently authenticated user.
     *
     * @return username, or null if unavailable
     */
    String findLoggedInUsername();

    /**
     * Performs programmatic login.
     *
     * @param username username
     * @param password password
     */
    void autologin(String username, String password);
}
