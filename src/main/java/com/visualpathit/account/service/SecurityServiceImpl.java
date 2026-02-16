package com.visualpathit.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Service;

/**
 * Security service implementation.
 */
@Service
public final class SecurityServiceImpl implements SecurityService {

    /**
     * Authentication manager used to authenticate users.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * User details service for loading user data.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Logger instance.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    public String findLoggedInUsername() {
        Object userDetails =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getDetails();

        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }

        return null;
    }

    @Override
    public void autologin(final String username, final String password) {
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        password,
                        userDetails.getAuthorities()
                );

        authenticationManager.authenticate(authToken);

        if (authToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authToken);
            LOGGER.debug(String.format(
                    "Auto login %s successfully!",
                    username
            ));
        }
    }
}
