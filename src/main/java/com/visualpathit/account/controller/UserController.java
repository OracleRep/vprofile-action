package com.visualpathit.account.controller;

import com.visualpathit.account.model.User;
import com.visualpathit.account.service.ProducerService;
import com.visualpathit.account.service.SecurityService;
import com.visualpathit.account.service.UserService;
import com.visualpathit.account.utils.MemcachedUtils;
import com.visualpathit.account.validator.UserValidator;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for user registration, login, profile, and cache-backed user lookup.
 */
@Controller
public final class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private static final int RABBIT_MESSAGE_COUNT = 20;

    private final UserService userService;
    private final SecurityService securityService;
    private final UserValidator userValidator;
    private final ProducerService producerService;
    private final MemcachedUtils memcachedUtils;

    public UserController(
            final UserService userService,
            final SecurityService securityService,
            final UserValidator userValidator,
            final ProducerService producerService,
            final MemcachedUtils memcachedUtils) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.producerService = producerService;
        this.memcachedUtils = memcachedUtils;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(final Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(
            @ModelAttribute("userForm") final User userForm,
            final BindingResult bindingResult,
            final Model model) {

        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(final Model model, final String error, final String logout) {
        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(final Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexHome(final Model model) {
        return "index_home";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getAllUsers(final Model model) {
        final List<User> users = userService.getList();
        model.addAttribute("users", users);
        return "userList";
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public String getOneUser(@PathVariable("id") final String id, final Model model) {
        try {
            final User cached = memcachedUtils.memcachedGetData(id);
            if (cached != null) {
                model.addAttribute("user", cached);
                model.addAttribute("Result", "Data is From Cache");
                return "user";
            }

            final User user = userService.findById(Long.parseLong(id));
            final String result = memcachedUtils.memcachedSetData(user, id);

            model.addAttribute("user", user);
            model.addAttribute("Result", result == null ? "Memcached Connection Failure !!" : result);
            return "user";

        } catch (RuntimeException ex) {
            LOGGER.error("Failed to load user id={}", id, ex);
            model.addAttribute("Result", "Error loading user");
            return "user";
        }
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public String userUpdate(@PathVariable("username") final String username, final Model model) {
        final User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "userUpdate";
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.POST)
    public String userUpdateProfile(
            @PathVariable("username") final String username,
            @ModelAttribute("user") final User userForm,
            final Model model) {

        final User user = userService.findByUsername(username);

        user.setUsername(userForm.getUsername());
        user.setUserEmail(userForm.getUserEmail());
        user.setDateOfBirth(userForm.getDateOfBirth());
        user.setFatherName(userForm.getFatherName());
        user.setMotherName(userForm.getMotherName());
        user.setGender(userForm.getGender());
        user.setLanguage(userForm.getLanguage());
        user.setMaritalStatus(userForm.getMaritalStatus());
        user.setNationality(userForm.getNationality());
        user.setPermanentAddress(userForm.getPermanentAddress());
        user.setTempAddress(userForm.getTempAddress());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setSecondaryPhoneNumber(userForm.getSecondaryPhoneNumber());
        user.setPrimaryOccupation(userForm.getPrimaryOccupation());
        user.setSecondaryOccupation(userForm.getSecondaryOccupation());
        user.setSkills(userForm.getSkills());
        user.setWorkingExperience(userForm.getWorkingExperience());

        userService.save(user);
        return "welcome";
    }

    /**
     * Produces sample messages into RabbitMQ for testing.
     *
     * @return rabbitmq view
     */
    @RequestMapping(value = "/user/rabbit", method = RequestMethod.GET)
    public String rabbitmqSetUp() {
        for (int i = 0; i < RABBIT_MESSAGE_COUNT; i++) {
            producerService.produceMessage(generateString());
        }
        return "rabbitmq";
    }

    private static String generateString() {
        final String uuid = UUID.randomUUID().toString();
        return "uuid = " + uuid;
    }
} 

