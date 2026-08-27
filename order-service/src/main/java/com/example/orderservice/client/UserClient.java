package com.example.orderservice.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import com.example.orderservice.dto.UserResponse;
import com.example.orderservice.exception.UserNotFoundException;
import com.example.orderservice.exception.UserServiceUnavailableException;

@Component
public class UserClient {

    private final RestClient restClient;

    public UserClient(RestClient.Builder restClientBuilder) {

        this.restClient = restClientBuilder
                .baseUrl("http://USER-SERVICE")
                .build();
    }

    public UserResponse getUserById(Long userId) {

        System.out.println("Calling USER-SERVICE through Eureka");
        System.out.println("User ID: " + userId);

        try {

            ResponseEntity<UserResponse> response =
                    restClient.get()
                            .uri("/api/users/{id}", userId)
                            .retrieve()
                            .toEntity(UserResponse.class);

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