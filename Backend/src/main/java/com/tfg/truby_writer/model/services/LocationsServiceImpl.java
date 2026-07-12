package com.tfg.truby_writer.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;


import com.tfg.truby_writer.model.daos.LocationDao;
import com.tfg.truby_writer.model.daos.LocationPointDao;


import com.tfg.truby_writer.model.entities.Location;
import com.tfg.truby_writer.model.entities.LocationPoint;
import com.tfg.truby_writer.model.entities.Plot;
import com.tfg.truby_writer.model.entities.Premise;
import com.tfg.truby_writer.model.services.EstructureService;
import com.tfg.truby_writer.model.enums.Enums;
import com.tfg.truby_writer.model.services.Block;


import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;




@Service
@Transactional
public class LocationsServiceImpl implements LocationsService{
    
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private LocationPointDao locationPointDao;
    @Autowired 
    private EstructureService estructureService;
    

    public Location addLocation(Long plotId, String name, String backgroundHistory, String backgroundImageUrl) throws DuplicateInstanceException, InstanceNotFoundException{
        Plot plot = estructureService.findPlotById(plotId);
        if (plot == null) {
            throw new InstanceNotFoundException("project.entities.plot", plotId);
        }
        if (locationDao.findByPlotIdAndName(plot.getId(), name) != null) {
            throw new DuplicateInstanceException("project.entities.location", name);
        }
        Location location = new Location();
        location.setPlot(plot);
        location.setName(name);
        location.setBackgroundHistory(backgroundHistory);
        location.setBackgroundImageUrl(backgroundImageUrl);
        locationDao.save(location);
        return location;
    }

    @Override
    public Location modifyLocation(Long locationId, String name, String backgroundHistory, String backgroundImageUrl) throws InstanceNotFoundException{
        Location location = locationDao.findById(locationId).orElseThrow(() -> new InstanceNotFoundException("project.entities.location", locationId));
        location.setName(name);
        location.setBackgroundHistory(backgroundHistory);
        location.setBackgroundImageUrl(backgroundImageUrl);
        locationDao.save(location);
        return location;
    }

    @Override
    public void deleteLocation(Long locationId) throws InstanceNotFoundException{
        Location location = locationDao.findById(locationId).orElseThrow(() -> new InstanceNotFoundException("project.entities.location", locationId));
        locationDao.deleteById(locationId);
    }

    @Override
    public Location findLocationById(Long locationId) throws InstanceNotFoundException{
        return locationDao.findById(locationId).orElseThrow(() -> new InstanceNotFoundException("project.entities.location", locationId));
    }


    @Override
    public Block<Location> findLocationsByFilter(Long plotId, String name) throws InstanceNotFoundException {

        Slice<Location> slice = locationDao.find(plotId,name, 0, 10);
        return new Block<>(slice.getContent(), slice.hasNext());
        
    }
    

    // LOCATION POINTS
    
    @Override
    public LocationPoint addLocationPoint(Long locationId, String name, String description, Float coordX, Float coordY, Enums.MarkerType markerType, String markerIcon) throws DuplicateInstanceException, InstanceNotFoundException{
        Location location = locationDao.findById(locationId).orElseThrow(() -> new InstanceNotFoundException("project.entities.location", locationId));
        if (locationPointDao.findByLocationIdAndName(location.getId(), name) != null) {
            throw new DuplicateInstanceException("project.entities.locationpoint", name);
        }
        if (coordX == null || coordX < 0.0f || coordX > 100.0f || coordY == null || coordY < 0.0f || coordY > 100.0f) {
            throw new IllegalArgumentException("Las coordenadas deben estar entre 0.0 y 100.0. X: " + coordX + ", Y: " + coordY);
        }
        LocationPoint locationPoint = new LocationPoint();
        locationPoint.setLocation(location);
        locationPoint.setName(name);
        locationPoint.setDescription(description);
        locationPoint.setCoordX(coordX);
        locationPoint.setCoordY(coordY);
        locationPoint.setMarkerType(markerType);
        locationPoint.setMarkerIcon(markerIcon);
        locationPointDao.save(locationPoint);
        return locationPoint;
    }

    @Override
    public LocationPoint modifyLocationPoint(Long locationPointId, String name, String description, Float coordX,  Float coordY, Enums.MarkerType markerType, String markerIcon) throws InstanceNotFoundException{
       
        if (coordX == null || coordX < 0.0f || coordX > 100.0f || coordY == null || coordY < 0.0f || coordY > 100.0f) {
            throw new IllegalArgumentException("Las coordenadas modificadas deben estar entre 0.0 y 100.0. X: " + coordX + ", Y: " + coordY);
        }
        
        LocationPoint locationPoint = locationPointDao.findById(locationPointId).orElseThrow(() -> new InstanceNotFoundException("project.entities.locationpoint", locationPointId));
        locationPoint.setName(name);
        locationPoint.setDescription(description);
        locationPoint.setCoordX(coordX);
        locationPoint.setCoordY(coordY);
        locationPoint.setMarkerType(markerType);
        locationPoint.setMarkerIcon(markerIcon);
        locationPointDao.save(locationPoint);
        return locationPoint;
    }

    @Override
    public void deleteLocationPoint(Long locationPointId) throws InstanceNotFoundException{
        locationPointDao.findById(locationPointId).orElseThrow(() -> new InstanceNotFoundException("project.entities.locationpoint", locationPointId));
        locationPointDao.deleteById(locationPointId);
    }

    @Override
    public LocationPoint findLocationPointById(Long locationPointId) throws InstanceNotFoundException{
        return locationPointDao.findById(locationPointId).orElseThrow(() -> new InstanceNotFoundException("project.entities.locationpoint", locationPointId));
    }
}
