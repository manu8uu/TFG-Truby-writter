package com.tfg.truby_writer.backend.rest.dtos.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterDto {

    private Long id;

    @NotBlank(message = "El nombre del personaje es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String name;

    private String bio;

    @Size(max = 500, message = "La URL de la imagen no puede superar los 500 caracteres")
    private String mainImageUrl;

    private Long projectId;
}