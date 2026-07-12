package com.tfg.truby_writer.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordParamsDto {

    @NotBlank
    private String oldPassword;

    @NotBlank
    @Size(min = 6, max = 255)
    private String newPassword;
    
}
