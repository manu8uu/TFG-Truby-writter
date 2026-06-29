package com.tfg.truby_writer.model.services.Character;


import com.tfg.truby_writer.model.entities.Character;
import com.tfg.truby_writer.model.services.Block;




import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException; 



public interface CharacterService {

    Character addCharacter( Long projectId, String name, String bio, String mainImageUrl) throws DuplicateInstanceException, InstanceNotFoundException;

    Character modifyCharacter(Long characterId, String name, String bio, String mainImageUrl) throws InstanceNotFoundException;

    void deleteCharacter(Long characterId) throws InstanceNotFoundException;

    Character getCharacterById(Long characterId) throws InstanceNotFoundException;

    Block<Character> findCharactersByFilter(Long projectId, String text) throws InstanceNotFoundException;

    Character findCharacterByName(Long projectId, String name) throws InstanceNotFoundException;


}

   