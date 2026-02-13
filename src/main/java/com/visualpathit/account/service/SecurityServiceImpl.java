package com.visualpathit.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Implementation of security-related functions.
 */
@Service
public final class SecurityServiceImpl implements SecurityService {

    /**
     * Logger instance.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(SecurityServiceImpl.class);

    /**
     * Authentication manager.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * User details service.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Creates a new security service implementation.
     *
     * @param authenticationManagerParam authentication manager
     * @param userDetailsServiceParam user details service
     */
    public SecurityServiceImpl(
            final AuthenticationManager authenticationManagerParam,
            final UserDetailsService userDetailsServiceParam) {
        this.authenticationManager = authenticationManagerParam;
        this.userDetailsService = userDetailsServiceParam;
    }

    /**
     * Returns the logged-in username, if available.
     *
     * @return logged-in username or null
     */
    @Override
    public String findLoggedInUsername() {
        final Object details = SecurityContextHolder.getContext()
                .getAuthentication()
                .getDetails();

        if (details instanceof UserDetails) {
            return ((UserDetails) details).getUsername();
        }
        return null;
    }

    /**
     * Authenticates a user and sets the Spring Security context.
     *
     * @param username username
     * @param password password
     */
    @Override
    public void autologin(final String username, final String password) {
        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);

        final UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        password,
                        userDetails.getAuthorities()
                );

        authenticationManager.authenticate(token);

        if (token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
            LOGGER.debug("Auto login {} successfully!", username);
        }
    }
}
