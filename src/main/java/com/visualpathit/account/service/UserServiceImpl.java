package com.visualpathit.account.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.visualpathit.account.model.User;
import com.visualpathit.account.repository.RoleRepository;
import com.visualpathit.account.repository.UserRepository;

/**
 * User service implementation.
 */
@Service
public final class UserServiceImpl implements UserService {

    /**
     * Repository for user persistence.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Repository for role persistence.
     */
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Encoder used to hash user passwords.
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(final User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getList() {
        return userRepository.findAll();
    }

    @Override
    public User findById(final long id) {
        return userRepository.findOne(id);
    }
}
