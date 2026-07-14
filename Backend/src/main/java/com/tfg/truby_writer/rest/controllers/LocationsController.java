package com.tfg.truby_writer.backend.rest.controllers;

import com.tfg.truby_writer.backend.rest.dtos.locations.*;
import com.tfg.truby_writer.model.entities.Location;
import com.tfg.truby_writer.model.entities.LocationPoint;
import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;
import com.tfg.truby_writer.model.services.Block;
import com.tfg.truby_writer.model.services.LocationsService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/structure")
public class LocationsController {

    private final LocationsService locationsService;

    public LocationsController(LocationsService locationsService) {
        this.locationsService = locationsService;
    }

    // LOCATIONS 

    @PostMapping("/plots/{plotId}/createLocation")
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto addLocation(@PathVariable Long plotId, @Valid @RequestBody LocationDto dto) 
            throws DuplicateInstanceException, InstanceNotFoundException {
        Location location = locationsService.addLocation(
                plotId, 
                dto.getName(), 
                dto.getBackgroundHistory(), 
                dto.getBackgroundImageUrl()
        );
        return LocationConversor.toLocationDto(location);
    }

    @PutMapping("/modify/{locationId}")
    public LocationDto modifyLocation(@PathVariable Long locationId, @Valid @RequestBody LocationDto dto) 
            throws InstanceNotFoundException {
        Location location = locationsService.modifyLocation(
                locationId, 
                dto.getName(), 
                dto.getBackgroundHistory(), 
                dto.getBackgroundImageUrl()
        );
        return LocationConversor.toLocationDto(location);
    }

    @DeleteMapping("/delete/{locationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable Long locationId) throws InstanceNotFoundException {
        locationsService.deleteLocation(locationId);
    }

    @GetMapping("/{locationId}")
    public LocationDto findLocationById(@PathVariable Long locationId) throws InstanceNotFoundException {
        Location location = locationsService.findLocationById(locationId);
        return LocationConversor.toLocationDto(location);
    }

    @GetMapping("/plots/{plotId}/search")
    public Block<LocationDto> findLocationsByFilter(@PathVariable Long plotId, @RequestParam String name) 
            throws InstanceNotFoundException {
        Block<Location> locationBlock = locationsService.findLocationsByFilter(plotId, name);
        return LocationConversor.toLocationBlockDto(locationBlock);
    }

    // LOCATION POINTS

    @PostMapping("/{locationId}/createPoint")
    @ResponseStatus(HttpStatus.CREATED)
    public LocationPointDto addLocationPoint(@PathVariable Long locationId, @Valid @RequestBody LocationPointDto dto) 
            throws DuplicateInstanceException, InstanceNotFoundException, IllegalArgumentException {
        LocationPoint point = locationsService.addLocationPoint(
                locationId, 
                dto.getName(), 
                dto.getDescription(), 
                dto.getCoordX(), 
                dto.getCoordY(), 
                dto.getMarkerType(), 
                dto.getMarkerIcon()
        );
        return LocationPointConversor.toLocationPointDto(point);
    }

    @PutMapping("/points/modify/{locationPointId}")
    public LocationPointDto modifyLocationPoint(@PathVariable Long locationPointId, @Valid @RequestBody LocationPointDto dto) 
            throws InstanceNotFoundException, IllegalArgumentException {
        LocationPoint point = locationsService.modifyLocationPoint(
                locationPointId, 
                dto.getName(), 
                dto.getDescription(), 
                dto.getCoordX(), 
                dto.getCoordY(), 
                dto.getMarkerType(), 
                dto.getMarkerIcon()
        );
        return LocationPointConversor.toLocationPointDto(point);
    }

    @DeleteMapping("/points/delete/{locationPointId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocationPoint(@PathVariable Long locationPointId) throws InstanceNotFoundException {
        locationsService.deleteLocationPoint(locationPointId);
    }

    @GetMapping("/points/{locationPointId}")
    public LocationPointDto findLocationPointById(@PathVariable Long locationPointId) throws InstanceNotFoundException {
        LocationPoint point = locationsService.findLocationPointById(locationPointId);
        return LocationPointConversor.toLocationPointDto(point);
    }
}