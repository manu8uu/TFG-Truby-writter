package com.tfg.truby_writer.backend.rest.dtos.Estructure;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PremiseDto {

    private Long id;
    
    @NotBlank
    private String premise;
    
    private String narrativePossibilities;
    private String narrativeChallenges;
    private String problems;
    private String foundingPrinciple;
    private String conflict;
    private String moralDecision;
    
    private Long plotId;
}