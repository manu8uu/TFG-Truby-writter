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
public class NetworkRelationshipDto {

    private Long id;
    
    @NotNull
    private String relationship;
    
    private String description;
    private Long networkId;
    
    @NotNull
    private Long nodeFromId;
    
    @NotNull
    private Long nodeToId;
}