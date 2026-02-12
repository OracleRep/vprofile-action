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
 * Security service implementation for authentication helpers.
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityServiceImpl(
            final AuthenticationManager authenticationManager,
            final UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

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

    @Override
    public void autologin(final String username, final String password) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        final UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        password,
                        userDetails.getAuthorities()
                );

        authenticationManager.authenticate(authToken);

        if (authToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authToken);
            LOGGER.debug("Auto login {} successfully!", username);
        }
    }
}
