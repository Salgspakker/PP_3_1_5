package ru.kata.spring.boot_security.demo.web.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.kata.spring.boot_security.demo.web.model.User;
import ru.kata.spring.boot_security.demo.web.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class UniqueValidator implements ConstraintValidator<UniqueField, User> {

    @Autowired
    private UserService userService;
    @Override
    public void initialize(UniqueField constraintAnnotation) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public boolean isValid(User user, ConstraintValidatorContext context) {
        User userFound = userService.findByUsername(user.getUsername());
        try {
            return userFound == null ||
                    user.getId().equals(userFound.getId());
        } catch (Exception e) {
            return false;
        }
    }
}


