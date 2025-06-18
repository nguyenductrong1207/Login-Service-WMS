package com.warehouse.loginservice.dto;

import java.util.List;

public record UserPageResponse(List<UserDto> userDtos,
                               Integer page,
                               Integer size,
                               long totalElements,
                               int totalPages) {
}
