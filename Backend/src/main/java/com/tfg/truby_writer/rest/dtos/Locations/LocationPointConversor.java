package com.tfg.truby_writer.backend.rest.dtos.locations;

import com.tfg.truby_writer.model.entities.LocationPoint;
import java.util.ArrayList;
import java.util.List;

public final class LocationPointConversor {

    private LocationPointConversor() {}

    public static LocationPointDto toLocationPointDto(LocationPoint point) {
        if (point == null) return null;
        return LocationPointDto.builder()
                .id(point.getId())
                .name(point.getName())
                .description(point.getDescription())
                .coordX(point.getCoordX())
                .coordY(point.getCoordY())
                .markerType(point.getMarkerType())
                .markerIcon(point.getMarkerIcon())
                .locationId(point.getLocation() != null ? point.getLocation().getId() : null)
                .build();
    }

    public static List<LocationPointDto> toLocationPointDtos(List<LocationPoint> points) {
        if (points == null) return List.of();
        List<LocationPointDto> dtos = new ArrayList<>();
        for (LocationPoint point : points) {
            if (point != null) {
                dtos.add(toLocationPointDto(point));
            }
        }
        return dtos;
    }
}