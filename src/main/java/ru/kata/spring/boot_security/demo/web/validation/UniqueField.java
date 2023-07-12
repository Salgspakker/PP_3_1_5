package ru.kata.spring.boot_security.demo.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
public @interface UniqueField {
    String message() default "Username is taken";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}