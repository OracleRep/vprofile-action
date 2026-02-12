package com.visualpathit.account.service;

import com.visualpathit.account.model.Role;
import com.visualpathit.account.model.User;
import com.visualpathit.account.repository.RoleRepository;
import com.visualpathit.account.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation for user management operations.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(
            final UserRepository userRepository,
            final RoleRepository roleRepository,
            final BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        final Set<Role> roles = new HashSet<>(roleRepository.findAll());
        user.setRoles(roles);

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