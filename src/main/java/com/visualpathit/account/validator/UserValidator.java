package com.visualpathit.account.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.visualpathit.account.model.User;
import com.visualpathit.account.service.UserService;

/**
 * Validator for {@link User} registration and profile updates.
 */
@Component
public final class UserValidator implements Validator {

    private static final int USERNAME_MIN_LENGTH = 6;
    private static final int USERNAME_MAX_LENGTH = 32;

    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 32;

    /**
     * User service used to check username uniqueness.
     */
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(final Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(final Object o, final Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "username",
                "NotEmpty"
        );

        int usernameLength = user.getUsername().length();
        if (usernameLength < USERNAME_MIN_LENGTH
                || usernameLength > USERNAME_MAX_LENGTH) {
            errors.rejectValue("username", "Size.userForm.username");
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "password",
                "NotEmpty"
        );

        int passwordLength = user.getPassword().length();
        if (passwordLength < PASSWORD_MIN_LENGTH
                || passwordLength > PASSWORD_MAX_LENGTH) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue(
                    "passwordConfirm",
                    "Diff.userForm.passwordConfirm"
            );
        }
    }
}
