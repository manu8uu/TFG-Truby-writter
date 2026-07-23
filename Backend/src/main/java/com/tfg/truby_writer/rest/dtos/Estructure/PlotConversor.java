package com.tfg.truby_writer.backend.rest.dtos.Estructure;

import com.tfg.truby_writer.model.entities.Plot;

import java.util.List;
import java.util.stream.Collectors;

public final class PlotConversor {

    private PlotConversor() {}

    public static PlotDto toPlotDto(Plot plot) {
        if (plot == null) return null;
        return PlotDto.builder()
                .id(plot.getId())
                .name(plot.getName())
                .dramaticSituation(plot.getDramaticSituation())
                .structWeaknessNeed(plot.getStructWeaknessNeed())
                .structDesire(plot.getStructDesire())
                .structAdversary(plot.getStructAdversary())
                .structPlan(plot.getStructPlan())
                .structStruggle(plot.getStructStruggle())
                .structSelfRevelation(plot.getStructSelfRevelation())
                .structNewEquilibrium(plot.getStructNewEquilibrium())
                .projectId(plot.getProject() != null ? plot.getProject().getId() : null)
                .build();
    }

    public static List<PlotDto> toPlotDtos(List<Plot> plots) {
        return plots.stream().map(PlotConversor::toPlotDto).collect(Collectors.toList());
    }

}