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
public class PlotDto {

    private Long id;
    
    @NotBlank
    private String name;
    
    private String dramaticSituation;

    private String structWeaknessNeed;
    private String structDesire;
    private String structAdversary;
    private String structPlan;
    private String structStruggle;
    private String structSelfRevelation;
    private String structNewEquilibrium;
    
    private Long projectId;
}