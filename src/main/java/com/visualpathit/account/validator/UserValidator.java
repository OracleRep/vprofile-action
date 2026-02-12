package com.visualpathit.account.validator;

import com.visualpathit.account.model.User;
import com.visualpathit.account.service.UserService;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for {@link User} registration.
 */
@Component
public class UserValidator implements Validator {

    private static final int USERNAME_MIN_LENGTH = 6;
    private static final int USERNAME_MAX_LENGTH = 32;
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 32;

    private final UserService userService;

    public UserValidator(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(final Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        final User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");

        if (user.getUsername() != null) {
            final int usernameLength = user.getUsername().length();
            if (usernameLength < USERNAME_MIN_LENGTH
                    || usernameLength > USERNAME_MAX_LENGTH) {
                errors.rejectValue("username", "Size.userForm.username");
            }

            if (userService.findByUsername(user.getUsername()) != null) {
                errors.rejectValue("username", "Duplicate.userForm.username");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");

        if (user.getPassword() != null) {
            final int passwordLength = user.getPassword().length();
            if (passwordLength < PASSWORD_MIN_LENGTH
                    || passwordLength > PASSWORD_MAX_LENGTH) {
                errors.rejectValue("password", "Size.userForm.password");
            }
        }

        if (user.getPassword() != null
                && user.getPasswordConfirm() != null
                && !user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}
