package com.tfg.truby_writer.backend.rest.dtos.Objects;

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
public class LineTimeDto {

    private Long id;

    private String name;

    private String description;

    @Size(max = 10, message = "El código de color no puede superar los 10 caracteres")
    private String colorCode;

    private Long plotId;

    private List<EventDto> events;
}