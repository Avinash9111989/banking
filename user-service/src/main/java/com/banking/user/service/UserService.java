package com.banking.user.service;

import com.banking.common.dto.AuthResponse;
import com.banking.common.dto.UserLoginRequest;
import com.banking.common.dto.UserRegistrationRequest;
import com.banking.common.dto.UserResponse;
import com.banking.common.enums.UserRole;
import com.banking.common.exception.BadRequestException;
import com.banking.common.exception.ResourceNotFoundException;
import com.banking.user.entity.Role;
import com.banking.user.entity.User;
import com.banking.user.repository.RoleRepository;
import com.banking.user.repository.UserRepository;
import com.banking.user.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Register a new user
     */
    public UserResponse registerUser(UserRegistrationRequest request) {
        log.info("Registering user with username: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("Username already exists: {}", request.getUsername());
            throw new BadRequestException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Email already exists: {}", request.getEmail());
            throw new BadRequestException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setIsActive(true);

        // Assign default USER role
        Role userRole = roleRepository.findByRoleName(UserRole.USER)
                .orElseThrow(() -> new ResourceNotFoundException("User role not found"));
        user.addRole(userRole);

        User savedUser = userRepository.save(user);
        log.info("User registered successfully with ID: {}", savedUser.getUserId());

        return convertToUserResponse(savedUser);
    }

    /**
     * Login user and return JWT token
     */
    public AuthResponse loginUser(UserLoginRequest request) {
        log.info("User login attempt for username: {}", request.getUsername());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            String token = jwtTokenProvider.generateToken(authentication);
            log.info("User logged in successfully: {}", request.getUsername());

            return new AuthResponse(
                    token,
                    "Bearer",
                    jwtTokenProvider.getJwtExpirationMs(),
                    convertToUserResponse(user)
            );
        } catch (Exception e) {
            log.error("Login failed for username: {}: {}", request.getUsername(), e.getMessage());
            throw new BadRequestException("Invalid username or password");
        }
    }

    /**
     * Get user by ID
     */
    public UserResponse getUserById(Integer userId) {
        log.debug("Fetching user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new ResourceNotFoundException("User not found with ID: " + userId);
                });

        return convertToUserResponse(user);
    }

    /**
     * Get user by username
     */
    public UserResponse getUserByUsername(String username) {
        log.debug("Fetching user with username: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", username);
                    return new ResourceNotFoundException("User not found with username: " + username);
                });

        return convertToUserResponse(user);
    }

    /**
     * Update user profile
     */
    public UserResponse updateUserProfile(Integer userId, UserResponse request) {
        log.info("Updating user profile for ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        User updatedUser = userRepository.save(user);
        log.info("User profile updated successfully for ID: {}", userId);

        return convertToUserResponse(updatedUser);
    }

    /**
     * Convert User entity to UserResponse DTO
     */
    private UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
