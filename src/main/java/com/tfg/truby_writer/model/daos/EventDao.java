package com.tfg.truby_writer.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.truby_writer.model.entities.Event;
import com.tfg.truby_writer.model.daos.Customized.CustomizedEventDao;

public interface EventDao extends JpaRepository<Event, Long>, CustomizedEventDao {

    Event findByLineTimeIdAndTitle(Long lineTimeId, String title);
    
}