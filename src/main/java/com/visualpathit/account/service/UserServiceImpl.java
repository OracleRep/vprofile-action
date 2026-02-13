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
 * User service implementation.
 */
@Service
public final class UserServiceImpl implements UserService {

    /**
     * User repository.
     */
    private final UserRepository userRepository;

    /**
     * Role repository.
     */
    private final RoleRepository roleRepository;

    /**
     * Password encoder.
     */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Creates a user service implementation.
     *
     * @param userRepositoryParam user repository
     * @param roleRepositoryParam role repository
     * @param passwordEncoderParam password encoder
     */
    public UserServiceImpl(
            final UserRepository userRepositoryParam,
            final RoleRepository roleRepositoryParam,
            final BCryptPasswordEncoder passwordEncoderParam) {
        this.userRepository = userRepositoryParam;
        this.roleRepository = roleRepositoryParam;
        this.passwordEncoder = passwordEncoderParam;
    }

    /**
     * Saves a user and assigns default roles.
     *
     * @param user user entity
     */
    @Override
    public void save(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        final Set<Role> roles = new HashSet<>(roleRepository.findAll());
        user.setRoles(roles);

        userRepository.save(user);
    }

    /**
     * Finds a user by username.
     *
     * @param username username
     * @return user or null
     */
    @Override
    public User findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Returns all users.
     *
     * @return list of users
     */
    @Override
    public List<User> getList() {
        return userRepository.findAll();
    }

    /**
     * Finds a user by id.
     *
     * @param id user id
     * @return user or null
     */
    @Override
    public User findById(final long id) {
        return userRepository.findOne(id);
    }
}
