package com.tfg.truby_writer.model.services;

import com.tfg.truby_writer.model.entities.Location;
import com.tfg.truby_writer.model.entities.LocationPoint;
import com.tfg.truby_writer.model.enums.Enums;
import com.tfg.truby_writer.model.services.Block;

import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException; 


public interface LocationsService {

    // LOCATIONS

    Location addLocation(Long plotId, String name, String backgroundHistory, String backgroundImageUrl) throws DuplicateInstanceException, InstanceNotFoundException;

    Location modifyLocation(Long locationId, String name, String backgroundHistory, String backgroundImageUrl) throws InstanceNotFoundException;

    void deleteLocation(Long locationId) throws InstanceNotFoundException;

    Location findLocationById(Long locationId) throws InstanceNotFoundException;

    Block<Location> findLocationsByFilter(Long plotId, String name) throws InstanceNotFoundException;

    // LOCATION POINTS
    
    LocationPoint addLocationPoint(Long locationId, String name, String description, Float coordX, Float coordY, Enums.MarkerType markerType, String markerIcon) throws DuplicateInstanceException, InstanceNotFoundException, IllegalArgumentException;
    
    LocationPoint modifyLocationPoint(Long locationPointId, String name, String description, Float coordX, Float coordY, Enums.MarkerType markerType, String markerIcon) throws InstanceNotFoundException, IllegalArgumentException;

    void deleteLocationPoint(Long locationPointId) throws InstanceNotFoundException;

    LocationPoint findLocationPointById(Long locationPointId) throws InstanceNotFoundException;


    }
