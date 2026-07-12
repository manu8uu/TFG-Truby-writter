package com.tfg.truby_writer.rest.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank(groups = {AllValidations.class}) 
    @Size(min = 6, max = 255)
    private String password;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    private Integer role;
    private Boolean blocked;

    public interface AllValidations {}
}