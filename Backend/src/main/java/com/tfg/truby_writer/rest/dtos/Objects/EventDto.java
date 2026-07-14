package com.tfg.truby_writer.backend.rest.dtos.Objects;

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
public class EventDto {

    private Long id;

    @NotBlank(message = "El título del evento es obligatorio")
    @Size(max = 150, message = "El título no puede superar los 150 caracteres")
    private String title;

    private String content;

    private Integer chapterNumber;

    private Integer chronoOrder;

    private Long timelineId;
}