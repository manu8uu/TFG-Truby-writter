package com.tfg.truby_writer.backend.rest.dtos.Estructure;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NetworkNodeDto {

    private Long id;
    
    @NotNull
    private String role;
    
    private Long networkId;
    
    @NotNull
    private Long characterId;
}