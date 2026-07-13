
package com.tfg.truby_writer.backend.rest.dtos.Estructure;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}