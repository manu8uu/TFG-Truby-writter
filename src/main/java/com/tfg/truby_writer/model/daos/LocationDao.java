package com.tfg.truby_writer.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.truby_writer.model.entities.Location;
import com.tfg.truby_writer.model.daos.Customized.CustomizedLocationDao;

public interface LocationDao extends JpaRepository<Location, Long>, CustomizedLocationDao {

    Location findByPlotIdAndName(Long plotId, String name);
    
}