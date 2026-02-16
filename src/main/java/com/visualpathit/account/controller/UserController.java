package com.visualpathit.account.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.visualpathit.account.model.User;
import com.visualpathit.account.service.ProducerService;
import com.visualpathit.account.service.SecurityService;
import com.visualpathit.account.service.UserService;
import com.visualpathit.account.utils.MemcachedUtils;
import com.visualpathit.account.validator.UserValidator;

/**
 * Web controller for user registration, login, profile and listing.
 */
@Controller
public final class UserController {

    /**
     * Number of messages to publish when testing RabbitMQ.
     */
    private static final int RABBITMQ_MESSAGE_COUNT = 20;

    /**
     * User service.
     */
    @Autowired
    private UserService userService;

    /**
     * Security service.
     */
    @Autowired
    private SecurityService securityService;

    /**
     * User validator.
     */
    @Autowired
    private UserValidator userValidator;

    /**
     * Producer service for RabbitMQ.
     */
    @Autowired
    private ProducerService producerService;

    /**
     * Displays the registration page.
     *
     * @param model the UI model
     * @return view name
     */
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(final Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    /**
     * Handles user registration.
     *
     * @param userForm the submitted user form
     * @param bindingResult binding result
     * @param model the UI model
     * @return redirect/view name
     */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(
            @ModelAttribute("userForm") final User userForm,
            final BindingResult bindingResult,
            final Model model) {

        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        System.out.println("User PWD: " + userForm.getPassword());
        userService.save(userForm);

        securityService.autologin(
                userForm.getUsername(),
                userForm.getPasswordConfirm()
        );

        return "redirect:/welcome";
    }

    /**
     * Displays the login page.
     *
     * @param model the UI model
     * @param error error flag
     * @param logout logout flag
     * @return view name
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(
            final Model model,
            final String error,
            final String logout) {

        System.out.println("Model data " + model.toString());

        if (error != null) {
            model.addAttribute(
                    "error",
                    "Your username and password is invalid."
            );
        }

        if (logout != null) {
            model.addAttribute(
                    "message",
                    "You have been logged out successfully."
            );
        }

        return "login";
    }

    /**
     * Displays the welcome page.
     *
     * @param model the UI model
     * @return view name
     */
    @RequestMapping(
            value = {"/", "/welcome"},
            method = RequestMethod.GET
    )
    public String welcome(final Model model) {
        return "welcome";
    }

    /**
     * Displays the index home page.
     *
     * @param model the UI model
     * @return view name
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexHome(final Model model) {
        return "index_home";
    }

    /**
     * Displays all users.
     *
     * @param model the UI model
     * @return view name
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getAllUsers(final Model model) {
        List<User> users = userService.getList();
        System.out.println("All User Data::: " + users);
        model.addAttribute("users", users);
        return "userList";
    }

    /**
     * Displays one user by id, using Memcached for caching when available.
     *
     * @param id the user id
     * @param model the UI model
     * @return view name
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public String getOneUser(
            @PathVariable(value = "id") final String id,
            final Model model) {

        String result = "";

        try {
            User cachedUser = MemcachedUtils.memcachedGetData(id);

            if (id != null && cachedUser != null) {
                result = "Data is From Cache";

                System.out.println("--------------------------------------------");
                System.out.println("Data is From Cache !!");
                System.out.println("--------------------------------------------");
                System.out.println("Father ::: " + cachedUser.getFatherName());

                model.addAttribute("user", cachedUser);
                model.addAttribute("Result", result);
            } else {
                User user = userService.findById(Long.parseLong(id));
                result = MemcachedUtils.memcachedSetData(user, id);

                if (result == null) {
                    result = "Memcached Connection Failure !!";
                }

                System.out.println("--------------------------------------------");
                System.out.println("Data is From Database");
                System.out.println("--------------------------------------------");
                System.out.println("Result ::: " + result);

                model.addAttribute("user", user);
                model.addAttribute("Result", result);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "user";
    }

    /**
     * Displays user profile update page.
     *
     * @param username the username
     * @param model the UI model
     * @return view name
     */
    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public String userUpdate(
            @PathVariable(value = "username") final String username,
            final Model model) {

        User user = userService.findByUsername(username);
        System.out.println("User Data::: " + user);

        model.addAttribute("user", user);
        return "userUpdate";
    }

    /**
     * Updates a user profile.
     *
     * @param username the username
     * @param userForm submitted user form
     * @param model the UI model
     * @return view name
     */
    @RequestMapping(value = "/user/{username}", method = RequestMethod.POST)
    public String userUpdateProfile(
            @PathVariable(value = "username") final String username,
            @ModelAttribute("user") final User userForm,
            final Model model) {

        User user = userService.findByUsername(username);

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
        user.setSecondaryPhoneNumber(
                userForm.getSecondaryPhoneNumber()
        );
        user.setPrimaryOccupation(userForm.getPrimaryOccupation());
        user.setSecondaryOccupation(userForm.getSecondaryOccupation());
        user.setSkills(userForm.getSkills());
        user.setWorkingExperience(userForm.getWorkingExperience());

        userService.save(user);

        return "welcome";
    }

    /**
     * Publishes a batch of messages to RabbitMQ (test endpoint).
     *
     * @return view name
     */
    @RequestMapping(value = "/user/rabbit", method = RequestMethod.GET)
    public String rabbitmqSetUp() {
        System.out.println("Rabbit mq method is called!!!");

        for (int i = 0; i < RABBITMQ_MESSAGE_COUNT; i++) {
            producerService.produceMessage(generateString());
        }

        return "rabbitmq";
    }

    private static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return "uuid = " + uuid;
    }
}
