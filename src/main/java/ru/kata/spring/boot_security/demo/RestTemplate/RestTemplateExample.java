package ru.kata.spring.boot_security.demo.RestTemplate;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class RestTemplateExample {
    static final String URL_EMPLOYEES = "http://94.198.50.185:7081/api/users";

    static final String URL_CREATE_EMPLOYEE = "http://94.198.50.185:7081/api/users";
    static final String URL_EMPLOYEES_XML = "http://localhost:8080/employees.xml";
    static final String URL_EMPLOYEES_JSON = "http://localhost:8080/employees.json";

    public static String getMethod() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(URL_EMPLOYEES, //
                HttpMethod.GET, entity, String.class);

        String header = response.getHeaders().get("Set-Cookie").toString().replaceAll("]|\\[", "");;
        return header;
    }

    public static void postMethod(String cookie) {
        User newEmployee = new User(3L, "James", "Brown", (byte) 22);
        String empNo = "E11";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));

        headers.add("Cookie", cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<User> requestBody = new HttpEntity<>(newEmployee, headers);

        ResponseEntity<String> response = restTemplate.exchange(URL_EMPLOYEES, //
                HttpMethod.POST, requestBody, String.class);
        String result = response.getBody();
        System.out.println("result of post :" + result);
    }

    public static void putMethod(String cookie) {
        User newEmployee = new User(3L, "Thomas", "Shelby", (byte) 22);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie", cookie);
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<User> requestBody = new HttpEntity<>(newEmployee, headers);

        ResponseEntity<String> response = restTemplate.exchange(URL_EMPLOYEES, //
                HttpMethod.PUT, requestBody, String.class);

        String result = response.getBody();

        System.out.println("result of put " + result);
    }

    public static void deleteMethod(String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie", cookie);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestBody = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URL_EMPLOYEES + "/3", //
                HttpMethod.DELETE, requestBody, String.class);
        String result = response.getBody();
        System.out.println("result of delete " + result);
    }

    public static void main(String[] args) {
        String cookie = getMethod();
        postMethod(cookie);
        putMethod(cookie);
        deleteMethod(cookie);
    }
}
