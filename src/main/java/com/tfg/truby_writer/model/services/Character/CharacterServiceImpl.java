package com.tfg.truby_writer.model.services.Character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.tfg.truby_writer.model.entities.Project;
import com.tfg.truby_writer.model.entities.Character;
import com.tfg.truby_writer.model.services.Block;

import com.tfg.truby_writer.model.daos.CharacterDao;
import com.tfg.truby_writer.model.services.Project.ProjectService;


import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;


@Service
@Transactional
public class CharacterServiceImpl implements CharacterService{

    @Autowired
    private CharacterDao characterDao;
    @Autowired
    private ProjectService projectService;
    
    public Character addCharacter(Long projectId, String name, String bio, String mainImageUrl) throws DuplicateInstanceException, InstanceNotFoundException{

        Project project = projectService.getProject(projectId);
        if (characterDao.findCharacterByProjectIdAndName(projectId, name) != null) {
            throw new DuplicateInstanceException("project.entities.character", name);
        }
        Character character = new Character();
        character.setName(name);
        character.setBio(bio);
        character.setMainImageUrl(mainImageUrl);
        character.setProject(project);
        characterDao.save(character);
        return character;
    }

    @Override
    public Character modifyCharacter(Long characterId, String name, String bio, String mainImageUrl) throws InstanceNotFoundException{
        Character character = characterDao.findById(characterId).orElseThrow(() -> new InstanceNotFoundException("project.entities.character", characterId));
        character.setName(name);
        character.setBio(bio);
        character.setMainImageUrl(mainImageUrl);
        characterDao.save(character);
        return character;
    }

    @Override
    public void deleteCharacter(Long characterId) throws InstanceNotFoundException{
        characterDao.findById(characterId).orElseThrow(() -> new InstanceNotFoundException("project.entities.character", characterId));
        characterDao.deleteById(characterId);
    }

    @Override
    public Character getCharacterById(Long characterId) throws InstanceNotFoundException{
        return characterDao.findById(characterId).orElseThrow(() -> new InstanceNotFoundException("project.entities.character", characterId));
    }

    @Override
    public Block<Character> findCharactersByFilter(Long projectId, String text) throws InstanceNotFoundException{
        Slice<Character> slice = characterDao.find(projectId, text, 0, 10);
        return new Block<>(slice.getContent(), slice.hasNext());
    }

    @Override
    public Character findCharacterByName(Long projectId, String name) throws InstanceNotFoundException{
        Character character = characterDao.findCharacterByProjectIdAndName(projectId, name);
        if (character == null) {
            throw new InstanceNotFoundException("project.entities.character", name);
        }
        return character;
    }

    @Override
    public Character findCharacterById(Long id) throws InstanceNotFoundException{
        Character character = characterDao.findCharacterById(id);
        if (character == null) {
            throw new InstanceNotFoundException("project.entities.character", id);
        }
        return character;
    }






    
}
