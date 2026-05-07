package com.banking.user.controller;

import com.banking.common.dto.ApiResponse;
import com.banking.common.dto.AuthResponse;
import com.banking.common.dto.UserLoginRequest;
import com.banking.common.dto.UserRegistrationRequest;
import com.banking.common.dto.UserResponse;
import com.banking.user.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Register a new user
     * POST /api/users/register
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(
            @Valid @RequestBody UserRegistrationRequest request) {
        log.info("Register request for username: {}", request.getUsername());
        
        UserResponse userResponse = userService.registerUser(request);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", userResponse));
    }

    /**
     * User login
     * POST /api/users/login
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> loginUser(
            @Valid @RequestBody UserLoginRequest request) {
        log.info("Login request for username: {}", request.getUsername());
        
        AuthResponse authResponse = userService.loginUser(request);
        
        return ResponseEntity.ok(ApiResponse.success("Login successful", authResponse));
    }

    /**
     * Get user by ID
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Integer id) {
        log.debug("Get user request for ID: {}", id);
        
        UserResponse userResponse = userService.getUserById(id);
        
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", userResponse));
    }

    /**
     * Get user by username
     * GET /api/users/username/{username}
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUsername(
            @PathVariable String username) {
        log.debug("Get user request for username: {}", username);
        
        UserResponse userResponse = userService.getUserByUsername(username);
        
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", userResponse));
    }

    /**
     * Update user profile
     * PUT /api/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserProfile(
            @PathVariable Integer id,
            @Valid @RequestBody UserResponse request) {
        log.info("Update user request for ID: {}", id);
        
        UserResponse userResponse = userService.updateUserProfile(id, request);
        
        return ResponseEntity.ok(ApiResponse.success("User profile updated successfully", userResponse));
    }

    /**
     * Health check endpoint
     * GET /api/users/health
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("User Service is running", "OK"));
    }
}
