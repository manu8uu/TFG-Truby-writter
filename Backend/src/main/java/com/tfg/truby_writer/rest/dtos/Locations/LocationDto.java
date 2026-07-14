package com.tfg.truby_writer.backend.rest.dtos.locations;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDto {

    private Long id;

    @NotBlank(message = "El nombre de la localización es obligatorio")
    @Size(max = 255, message = "El nombre no puede superar los 255 caracteres")
    private String name;

    private String backgroundHistory;

    @Size(max = 500, message = "La URL de la imagen de fondo no puede superar los 500 caracteres")
    private String backgroundImageUrl;

    private Long plotId;

    private List<LocationPointDto> points;
}