package com.tfg.truby_writer.backend.rest.controllers;

import com.tfg.truby_writer.backend.rest.dtos.Objects.*;
import com.tfg.truby_writer.model.entities.Character;
import com.tfg.truby_writer.model.entities.Event;
import com.tfg.truby_writer.model.entities.LineTime;
import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;
import com.tfg.truby_writer.model.services.Block;
import com.tfg.truby_writer.model.services.ObjectsService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/structure")
public class ObjectsController {

    private final ObjectsService objectsService;

    public ObjectsController(ObjectsService objectsService) {
        this.objectsService = objectsService;
    }

    // CHARACTERS 

    @PostMapping("/projects/{projectId}/createCharacter")
    @ResponseStatus(HttpStatus.CREATED)
    public CharacterDto addCharacter(@PathVariable Long projectId, @Valid @RequestBody CharacterDto dto) 
            throws DuplicateInstanceException, InstanceNotFoundException {
        Character character = objectsService.addCharacter(projectId, dto.getName(), dto.getBio(), dto.getMainImageUrl());
        return CharacterConversor.toCharacterDto(character);
    }

    @PutMapping("/characters/modify/{characterId}")
    public CharacterDto modifyCharacter(@PathVariable Long characterId, @Valid @RequestBody CharacterDto dto) 
            throws InstanceNotFoundException {
        Character character = objectsService.modifyCharacter(characterId, dto.getName(), dto.getBio(), dto.getMainImageUrl());
        return CharacterConversor.toCharacterDto(character);
    }

    @DeleteMapping("/characters/delete/{characterId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharacter(@PathVariable Long characterId) throws InstanceNotFoundException {
        objectsService.deleteCharacter(characterId);    
    }

    @GetMapping("/characters/{characterId}")
    public CharacterDto getCharacterById(@PathVariable Long characterId) throws InstanceNotFoundException {
        Character character = objectsService.getCharacterById(characterId); 
        return CharacterConversor.toCharacterDto(character);
    }

    @GetMapping("/projects/{projectId}/characters/search")
    public Block<CharacterDto> findCharactersByFilter(@PathVariable Long projectId, @RequestParam String text) 
            throws InstanceNotFoundException {
        Block<Character> characterBlock = objectsService.findCharactersByFilter(projectId, text);   
        return CharacterConversor.toCharacterBlockDto(characterBlock);
    }

    @GetMapping("/projects/{projectId}/characters/byName")
    public CharacterDto findCharacterByName(@PathVariable Long projectId, @RequestParam String name) 
            throws InstanceNotFoundException {
        Character character = objectsService.findCharacterByName(projectId, name);  
        return CharacterConversor.toCharacterDto(character);
    }

    // EVENTS 

    @PostMapping("/plots/{timelineId}/createEvent")
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto addEvent(@PathVariable Long timelineId, @Valid @RequestBody EventDto dto) 
            throws DuplicateInstanceException, InstanceNotFoundException {
        Event event = objectsService.addEvent(
                timelineId, 
                dto.getTitle(), 
                dto.getContent(), 
                dto.getChapterNumber(), 
                dto.getChronoOrder()
        );  
        return EventConversor.toEventDto(event);
    }

    @PutMapping("/events/modify/{eventId}")
    public EventDto modifyEvent(@PathVariable Long eventId, @Valid @RequestBody EventDto dto) 
            throws InstanceNotFoundException, DuplicateInstanceException {
        Event event = objectsService.modifyEvent(
                eventId, 
                dto.getTitle(), 
                dto.getContent(), 
                dto.getChapterNumber(), 
                dto.getChronoOrder()
        );  
        return EventConversor.toEventDto(event);
    }

    @DeleteMapping("/events/delete/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Long eventId) throws InstanceNotFoundException {
        objectsService.deleteEvent(eventId);    
    }

    @GetMapping("/plots/{timelineId}/events/search")
    public Block<EventDto> findEventsByName(@PathVariable Long timelineId, @RequestParam String title) 
            throws InstanceNotFoundException {
        Block<Event> eventBlock = objectsService.findEventsByName(timelineId, title);   
        return EventConversor.toEventBlockDto(eventBlock);
    }

    // LINE TIMES

    @GetMapping("/plots/{plotId}/lineTime")
    public LineTimeDto getLineTimeByPlot(@PathVariable Long plotId) throws InstanceNotFoundException {
        LineTime lineTime = objectsService.getLineTimeByPlot(plotId);       
        return LineTimeConversor.toLineTimeDto(lineTime);
    }
}