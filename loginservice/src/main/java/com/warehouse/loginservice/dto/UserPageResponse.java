package com.warehouse.loginservice.dto;

import java.util.List;

public record UserPageResponse(List<UserDto> userDtos,
                               Integer pageNumber,
                               Integer pageSize,
                               long totalElements,
                               int totalPages,
                               boolean isLast) {
}
