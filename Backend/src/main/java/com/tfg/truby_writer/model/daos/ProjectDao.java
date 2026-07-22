package com.tfg.truby_writer.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.truby_writer.model.entities.Project;
import java.util.List;

 

public interface ProjectDao extends JpaRepository<Project, Long> {

    boolean existsByName(String name);

    java.util.Optional<Project> findByName(String name);

    List <Project> findAllProjectsByUserId(Long userId);
    
}
