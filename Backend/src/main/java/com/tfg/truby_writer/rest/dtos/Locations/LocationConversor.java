package com.tfg.truby_writer.backend.rest.dtos.locations;

import com.tfg.truby_writer.model.entities.Location;
import com.tfg.truby_writer.model.services.Block;
import java.util.ArrayList;
import java.util.List;

public final class LocationConversor {

    private LocationConversor() {}

    public static LocationDto toLocationDto(Location location) {
        if (location == null) return null;
        return LocationDto.builder()
                .id(location.getId())
                .name(location.getName())
                .backgroundHistory(location.getBackgroundHistory())
                .backgroundImageUrl(location.getBackgroundImageUrl())
                .plotId(location.getPlot() != null ? location.getPlot().getId() : null)
                .points(LocationPointConversor.toLocationPointDtos(location.getPoints())) // Cascada
                .build();
    }

    public static List<LocationDto> toLocationDtos(List<Location> locations) {
        if (locations == null) return List.of();
        List<LocationDto> dtos = new ArrayList<>();
        for (Location location : locations) {
            if (location != null) {
                dtos.add(toLocationDto(location));
            }
        }
        return dtos;
    }

    public static Block<LocationDto> toLocationBlockDto(Block<Location> locationBlock) {
        List<LocationDto> items = toLocationDtos(locationBlock.getItems());
        return new Block<>(items, locationBlock.getExistMoreItems());
    }
}