package com.visualpathit.account.validator;

import com.visualpathit.account.model.User;
import com.visualpathit.account.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator implementation for {@link User} entity.
 */
@Component
public final class UserValidator implements Validator {

    /**
     * User service used to validate duplicate usernames.
     */
    @Autowired
    private UserService userService;

    /**
     * Minimum username length.
     */
    private static final int USERNAME_MIN = 6;

    /**
     * Maximum username length.
     */
    private static final int USERNAME_MAX = 32;

    /**
     * Minimum password length.
     */
    private static final int PASSWORD_MIN = 8;

    /**
     * Maximum password length.
     */
    private static final int PASSWORD_MAX = 32;

    /**
     * Checks if the validator supports the given class.
     *
     * @param aClass class type
     * @return true if supported
     */
    @Override
    public boolean supports(final Class<?> aClass) {
        return User.class.equals(aClass);
    }

    /**
     * Validates the user object.
     *
     * @param target the object to validate
     * @param errors validation errors container
     */
    @Override
    public void validate(final Object target, final Errors errors) {

        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "username",
                "NotEmpty"
        );

        if (user.getUsername().length() < USERNAME_MIN
                || user.getUsername().length() > USERNAME_MAX) {
            errors.rejectValue(
                    "username",
                    "Size.userForm.username"
            );
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue(
                    "username",
                    "Duplicate.userForm.username"
            );
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "password",
                "NotEmpty"
        );

        if (user.getPassword().length() < PASSWORD_MIN
                || user.getPassword().length() > PASSWORD_MAX) {
            errors.rejectValue(
                    "password",
                    "Size.userForm.password"
            );
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue(
                    "passwordConfirm",
                    "Diff.userForm.passwordConfirm"
            );
        }
    }
}
