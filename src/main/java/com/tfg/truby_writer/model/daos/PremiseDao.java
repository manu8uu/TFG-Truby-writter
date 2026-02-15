package com.tfg.truby_writer.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.truby_writer.model.entities.Premise;

public interface PremiseDao extends JpaRepository<Premise, Long> {
    
}
