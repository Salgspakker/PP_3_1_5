package ru.kata.spring.boot_security.demo.web.exception_handling;

public class UserIncorrectData {
    private String info;

    public UserIncorrectData() {

    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
