package com.tfg.truby_writer.rest.dtos;

import com.tfg.truby_writer.model.entities.User;
import com.tfg.truby_writer.model.enums.Enums.UserRole;

public class UserConversor {

    private UserConversor() {}

    public static final UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole() != null ? user.getRole().name() : null)
                .blocked(user.getBlocked())
                .build();
    }

    public static final User toUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .role(userDto.getRole() != null ? UserRole.valueOf(userDto.getRole()) : UserRole.USER)
                .blocked(userDto.getBlocked() != null ? userDto.getBlocked() : false)
                .build();
    }

    public static final AuthenticatedUserDto toAuthenticatedUserDto(String serviceToken, User user) {
        return new AuthenticatedUserDto(serviceToken, toUserDto(user));
    }
}