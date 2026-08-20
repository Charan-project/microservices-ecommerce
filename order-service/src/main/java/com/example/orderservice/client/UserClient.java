package com.example.orderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;
import com.example.orderservice.dto.UserResponse;
import com.example.orderservice.exception.UserNotFoundException;
import com.example.orderservice.exception.UserServiceUnavailableException;

@Component
public class UserClient {

    private final RestTemplate restTemplate;
    private final String userServiceBaseUrl;

    public UserClient(
            RestTemplate restTemplate,
            @Value("${user.service.base-url}") String userServiceBaseUrl) {

        this.restTemplate = restTemplate;
        this.userServiceBaseUrl = userServiceBaseUrl;
    }

//    public UserResponse getUserById(Long userId) {
//
//        String url = userServiceBaseUrl + "/api/users" + userId;
//
//        try {
//
//            ResponseEntity<UserResponse> response =
//                    restTemplate.getForEntity(
//                            url,
//                            UserResponse.class
//                    );
//
//            return response.getBody();
//
//        } catch (HttpClientErrorException.NotFound e) {
//
//            return null;
//        }
//    }
    
//    public UserResponse getUserById(Long userId) {
//
//        String url = userServiceBaseUrl + "/api/users/" + userId;
//
//        System.out.println("=================================");
//        System.out.println("Calling User Service");
//        System.out.println("User ID: " + userId);
//        System.out.println("URL: " + url);
//        System.out.println("=================================");
//
//        try {
//
//            ResponseEntity<UserResponse> response =
//                    restTemplate.getForEntity(
//                            url,
//                            UserResponse.class
//                    );
//
//            System.out.println("User Service Status: "
//                    + response.getStatusCode());
//
//            System.out.println("User Service Response: "
//                    + response.getBody());
//
//            return response.getBody();
//
//        } catch (HttpClientErrorException.NotFound e) {
//
//            System.out.println("USER NOT FOUND: " + url);
//
//            return null;
//        }
//    }
    
    public UserResponse getUserById(Long userId) {

        String url = userServiceBaseUrl + "/api/users/" + userId;

        System.out.println("Calling User Service: " + url);

        try {

            ResponseEntity<UserResponse> response =
                    restTemplate.getForEntity(
                            url,
                            UserResponse.class
                    );

            return response.getBody();

        } catch (HttpClientErrorException.NotFound e) {

            throw new UserNotFoundException(
                    "User not found: " + userId
            );

        } catch (ResourceAccessException e) {

            throw new UserServiceUnavailableException(
                    "User Service is unavailable",
                    e
            );
        }
    }
    
}