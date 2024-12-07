package com.warehouse.loginservice.service.impl;

import com.warehouse.loginservice.dto.JwtAuthenticationResponse;
import com.warehouse.loginservice.dto.UserDto;
import com.warehouse.loginservice.dto.UserPageResponse;
import com.warehouse.loginservice.entity.RefreshToken;
import com.warehouse.loginservice.entity.User;
import com.warehouse.loginservice.entity.UserRole;
import com.warehouse.loginservice.exceptions.UserNotFoundException;
import com.warehouse.loginservice.repository.UserRepository;
import com.warehouse.loginservice.service.RefreshTokenService;
import com.warehouse.loginservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTServiceImpl jwtService;

    private final RefreshTokenService refreshTokenService;

    @Override
    public UserDto addUser(UserDto userDto) {
        // Map DTO to User entity
        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(UserRole.valueOf(userDto.getRole())) // Convert role string to UserRole enum
                .build();

        User savedUser = userRepository.save(user);
        String accessToken = jwtService.generateToken(savedUser, null); // Default expiration (30 minutes)
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(savedUser.getEmail());

        JwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();

        // Use the Builder Pattern to map User to UserDto
        return UserDto.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .role(savedUser.getRole().name()) // Convert UserRole enum to string
                .build();
    }

    @Override
    public UserDto getUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id = " + userId));

        return UserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        // Use Stream API to map each User entity to a UserDto object using the Builder Pattern (Instead of using For Loop and ArrayList)
        return users.stream()
                .map(user -> UserDto.builder()
                        .userId(user.getUserId())
                        .name(user.getName())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .role(user.getRole().name())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Integer userId, UserDto userDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id = " + userId));

        existingUser.setName(userDto.getName());
        existingUser.setUsername(userDto.getUsername());
        existingUser.setEmail(userDto.getEmail());
        // No need to update password unless explicitly provided, for security reasons
        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        existingUser.setRole(UserRole.valueOf(userDto.getRole())); // Convert string to UserRole enum

        User updatedUser = userRepository.save(existingUser);

        return UserDto.builder()
                .userId(updatedUser.getUserId())
                .name(updatedUser.getName())
                .username(updatedUser.getUsername())
                .email(updatedUser.getEmail())
                .role(updatedUser.getRole().name())
                .build();
    }

    @Override
    public UserPageResponse getAllUsersWithPagination(Integer pageNumber, Integer pageSize) {
        Pageable pageAble = PageRequest.of(pageNumber, pageSize);

        Page<User> userPages = userRepository.findAll(pageAble);

        List<UserDto> userDtos = userPages.getContent().stream()
                .map(user -> UserDto.builder()
                        .userId(user.getUserId())
                        .name(user.getName())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .role(user.getRole().name())
                        .build())
                .collect(Collectors.toList());

        return new UserPageResponse(
                userDtos,
                pageNumber,
                pageSize,
                userPages.getTotalElements(),
                userPages.getTotalPages(),
                userPages.isLast()
        );
    }

    @Override
    public UserPageResponse getAllUsersWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String dir) {
        Sort sort = dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> userPages = userRepository.findAll(pageable);

        List<UserDto> userDtos = userPages.getContent().stream()
                .map(user -> UserDto.builder()
                        .userId(user.getUserId())
                        .name(user.getName())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .role(user.getRole().name())
                        .build())
                .collect(Collectors.toList());

        return new UserPageResponse(
                userDtos,
                pageNumber,
                pageSize,
                userPages.getTotalElements(),
                userPages.getTotalPages(),
                userPages.isLast()
        );
    }

}
