package com.tfg.truby_writer.rest.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class AuthenticatedUserDto {
    private String serviceToken;
    private UserDto userDto;
}