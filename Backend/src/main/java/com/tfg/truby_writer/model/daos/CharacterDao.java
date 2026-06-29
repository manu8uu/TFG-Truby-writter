package com.tfg.truby_writer.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.truby_writer.model.daos.Customized.CustomizedCharacterDao;
import com.tfg.truby_writer.model.entities.Character;

public interface CharacterDao extends JpaRepository<Character, Long>, CustomizedCharacterDao {

    Character findCharacterByProjectIdAndName(Long projectId, String name);

    Character findCharacterById(Long id);

    
    
}
