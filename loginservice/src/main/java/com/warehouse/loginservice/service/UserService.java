package com.warehouse.loginservice.service;

import com.warehouse.loginservice.dto.UserDto;
import com.warehouse.loginservice.dto.UserPageResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface UserService {

    UserDto addUser(UserDto userDto) throws IOException;

    UserDto getUser(Integer userId);

    List<UserDto> getAllUsers();

    UserDto updateUser(Integer userId, UserDto userDto) throws IOException;

    UserPageResponse getAllUsersWithPagination(Integer pageNumber, Integer pageSize);

    UserPageResponse getAllUsersWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String dir);

}
