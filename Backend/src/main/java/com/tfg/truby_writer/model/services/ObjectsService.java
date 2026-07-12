package com.tfg.truby_writer.model.services;

import java.nio.channels.OverlappingFileLockException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.tfg.truby_writer.model.entities.Character;

import com.tfg.truby_writer.model.enums.Enums;
import com.tfg.truby_writer.model.services.Block;
import com.tfg.truby_writer.model.entities.Event;
import com.tfg.truby_writer.model.entities.LineTime;

import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException; 

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

public interface ObjectsService {

    //CHARACTERS

    Character addCharacter( Long projectId, String name, String bio, String mainImageUrl) throws DuplicateInstanceException, InstanceNotFoundException;

    Character modifyCharacter(Long characterId, String name, String bio, String mainImageUrl) throws InstanceNotFoundException;

    void deleteCharacter(Long characterId) throws InstanceNotFoundException;

    Character getCharacterById(Long characterId) throws InstanceNotFoundException;

    Block<Character> findCharactersByFilter(Long projectId, String text) throws InstanceNotFoundException;

    Character findCharacterByName(Long projectId, String name) throws InstanceNotFoundException;


    // EVENTS

    Event addEvent(Long timelineId, String name, String description, Integer chapterNumber, Integer chronoOrder) throws DuplicateInstanceException, InstanceNotFoundException;

    Event modifyEvent(Long id, String title, String content, Integer chapterNumber, Integer chronoOrder) throws InstanceNotFoundException, DuplicateInstanceException, IllegalArgumentException;

    void deleteEvent(Long eventId) throws InstanceNotFoundException;

    Block<Event> findEventsByName(Long timelineId, String title) throws InstanceNotFoundException;


    // LINE TIMES


    //FALTA HACER LA FUNCIÓN QUE ORDENE LOS EVENTOS DE UNA LINETIME POR ORDEN CRONOLOGICO O CAPITULAR

    LineTime getLineTimeByPlot(Long plotId) throws InstanceNotFoundException;
    
}
