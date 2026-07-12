package com.tfg.truby_writer.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginParamsDto {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    public void setUserName(String userName) {
        this.userName = userName != null ? userName.trim() : null;
    }
}