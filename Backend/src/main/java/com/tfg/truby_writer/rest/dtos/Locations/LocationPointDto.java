package com.tfg.truby_writer.backend.rest.dtos.locations;

import com.tfg.truby_writer.model.enums.Enums.MarkerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationPointDto {

    private Long id;

    @NotBlank(message = "El nombre del punto de interés es obligatorio")
    @Size(max = 255, message = "El nombre no puede superar los 255 caracteres")
    private String name;

    private String description;

    @NotNull(message = "La coordenada X es obligatoria")
    private Float coordX;

    @NotNull(message = "La coordenada Y es obligatoria")
    private Float coordY;

    private MarkerType markerType;

    @Size(max = 50, message = "El icono de marcador no puede superar los 50 caracteres")
    private String markerIcon;

    private Long locationId;
}