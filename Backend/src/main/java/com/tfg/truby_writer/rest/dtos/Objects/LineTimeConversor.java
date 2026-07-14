package com.tfg.truby_writer.backend.rest.dtos.Objects;

import com.tfg.truby_writer.model.entities.LineTime;

public final class LineTimeConversor {

    private LineTimeConversor() {}

    public static LineTimeDto toLineTimeDto(LineTime lineTime) {
        if (lineTime == null) return null;
        return LineTimeDto.builder()
                .id(lineTime.getId())
                .name(lineTime.getName())
                .description(lineTime.getDescription())
                .colorCode(lineTime.getColorCode())
                .plotId(lineTime.getPlot() != null ? lineTime.getPlot().getId() : null)
                .events(EventConversor.toEventDtos(lineTime.getEvents())) // Mapea cascada de eventos asociados
                .build();
    }
}