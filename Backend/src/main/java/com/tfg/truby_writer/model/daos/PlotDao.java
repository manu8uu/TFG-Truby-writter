package com.tfg.truby_writer.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.truby_writer.model.entities.Plot;
import java.util.List;


 

public interface PlotDao extends JpaRepository<Plot, Long>{

    Plot findByProjectIdAndName(Long projectId, String name);

    List<Plot> getAllPlotsByProjectId(Long projectId);
    
}
