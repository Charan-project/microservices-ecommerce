package com.example.userservice.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.userservice.model.User;

@Service
public class UserService {

    private final Map<Long, User> users = new HashMap<>();

    public UserService() {

        users.put(1L, new User(1L, "Charan", "charan@gmail.com"));

        users.put(2L, new User(2L, "Rahul", "rahul@gmail.com"));

        users.put(3L, new User(3L, "Anita", "anita@gmail.com"));
    }
   // User u = new User(null, null, null);

    public User getUserById(Long id) {

        return users.get(id);
    }
}