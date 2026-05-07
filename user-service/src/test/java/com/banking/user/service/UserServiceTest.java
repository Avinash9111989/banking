package com.banking.user.service;

import com.banking.common.dto.UserRegistrationRequest;
import com.banking.common.exception.BadRequestException;
import com.banking.user.entity.Role;
import com.banking.user.entity.User;
import com.banking.user.repository.RoleRepository;
import com.banking.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    private UserRegistrationRequest registrationRequest;

    @BeforeEach
    public void setUp() {
        registrationRequest = new UserRegistrationRequest();
        registrationRequest.setUsername("testuser");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPassword("password123");
        registrationRequest.setFirstName("Test");
        registrationRequest.setLastName("User");
    }

    @Test
    public void testRegisterUserSuccess() {
        // Arrange
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded_password");
        
        Role userRole = new Role();
        when(roleRepository.findByRoleName(any())).thenReturn(Optional.of(userRole));
        
        User savedUser = new User();
        savedUser.setUserId(1);
        savedUser.setUsername("testuser");
        savedUser.setEmail("test@example.com");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        var response = userService.registerUser(registrationRequest);

        // Assert
        assertNotNull(response);
        assertEquals("testuser", response.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testRegisterUserWithDuplicateUsername() {
        // Arrange
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> userService.registerUser(registrationRequest));
    }

    @Test
    public void testRegisterUserWithDuplicateEmail() {
        // Arrange
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> userService.registerUser(registrationRequest));
    }
}
