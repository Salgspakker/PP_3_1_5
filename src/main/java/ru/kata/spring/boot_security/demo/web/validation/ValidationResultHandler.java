package ru.kata.spring.boot_security.demo.web.validation;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

public class ValidationResultHandler {
    public String handleBindingResult(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        List<ObjectError> errors = bindingResult.getAllErrors();
        for (ObjectError error : errors) {
            errorMessage.append(error.getDefaultMessage()).append("\n");
        }
        return String.valueOf(errorMessage);
    }
}
