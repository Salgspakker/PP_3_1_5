package ru.kata.spring.boot_security.demo.web.exception_handling;

public class UserValidationException extends RuntimeException {
    public UserValidationException(String message) {
        super(message);
    }
}
