package com.warehouse.loginservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.warehouse.loginservice.dto.UserDto;
import com.warehouse.loginservice.dto.UserPageResponse;
import com.warehouse.loginservice.service.UserService;
import com.warehouse.loginservice.config.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserDto> addUserHandler(@RequestPart String userDto) throws IOException {
        UserDto dto = convertToUserDto(userDto);
        return new ResponseEntity<>(userService.addUser(dto), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUserHandler(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<UserDto>> getAllUsersHandler() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/user/update/{userId}")
    public ResponseEntity<UserDto> updateUserHandler(@PathVariable Integer userId,
                                                     @RequestPart String userDtoObj) throws IOException {
        UserDto userDto = convertToUserDto(userDtoObj);
        return ResponseEntity.ok(userService.updateUser(userId, userDto));
    }

    @GetMapping("/user/allUsersPage")
    public ResponseEntity<UserPageResponse> getUsersWithPagination(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
    ) {
        return ResponseEntity.ok(userService.getAllUsersWithPagination(pageNumber, pageSize));
    }

    @GetMapping("/users") // "/allUsersPageSort"
    public ResponseEntity<UserPageResponse> getUsersWithPaginationAndSorting(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String dir
    ) {
        return ResponseEntity.ok(userService.getAllUsersWithPaginationAndSorting(pageNumber, pageSize, sortBy, dir));
    }

    private UserDto convertToUserDto(String userDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(userDtoObj, UserDto.class);
    }

}
