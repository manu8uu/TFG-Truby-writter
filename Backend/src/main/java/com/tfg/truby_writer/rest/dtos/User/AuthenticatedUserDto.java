package com.tfg.truby_writer.rest.dtos.User;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class AuthenticatedUserDto {
    private String serviceToken;
    private UserDto userDto;
}