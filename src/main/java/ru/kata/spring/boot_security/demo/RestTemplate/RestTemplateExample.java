package ru.kata.spring.boot_security.demo.RestTemplate;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class RestTemplateExample {
    static final String URL_EMPLOYEES = "http://94.198.50.185:7081/api/users";

    static final String URL_EMPLOYEES_XML = "http://localhost:8080/employees.xml";
    static final String URL_EMPLOYEES_JSON = "http://localhost:8080/employees.json";


    public static void main(String[] args) {

        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        // Request to return JSON format
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("my_other_key", "my_other_value");

        // HttpEntity<String>: To get result as String.
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        // RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Send request with GET method, and Headers.
        ResponseEntity<String> response = restTemplate.exchange(URL_EMPLOYEES, //
                HttpMethod.GET, entity, String.class);

        String header = response.getHeaders().get("Set-Cookie").toString();
        String result = response.getBody();
        System.out.println(header);
        System.out.println(result);
    }
        }
