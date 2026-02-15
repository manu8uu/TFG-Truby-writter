package com.tfg.truby_writer.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.truby_writer.model.entities.Event;

public interface EventDao extends JpaRepository<Event, Long> {
    
}