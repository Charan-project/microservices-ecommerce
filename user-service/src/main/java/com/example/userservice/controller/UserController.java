package com.example.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.userservice.dto.UserResponse;
import com.example.userservice.model.User;
import com.example.userservice.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {

		if (id == null || id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		User user = userService.getUserById(id);

		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		UserResponse response = new UserResponse(user.getId(), user.getName(), user.getEmail());

		return ResponseEntity.ok(response);
	}
}