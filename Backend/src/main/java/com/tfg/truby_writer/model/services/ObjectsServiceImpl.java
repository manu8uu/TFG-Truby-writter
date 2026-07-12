package com.tfg.truby_writer.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;


import com.tfg.truby_writer.model.services.Block;
import com.tfg.truby_writer.model.entities.Event;
import com.tfg.truby_writer.model.entities.LineTime;
import com.tfg.truby_writer.model.entities.Plot;
import com.tfg.truby_writer.model.entities.Character;
import com.tfg.truby_writer.model.entities.Project;

import com.tfg.truby_writer.model.daos.CharacterDao;
import com.tfg.truby_writer.model.daos.EventDao;
import com.tfg.truby_writer.model.daos.LineTimeDao;
import com.tfg.truby_writer.model.daos.PlotDao;

import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException; 
import org.springframework.data.domain.PageRequest;

@Service
@Transactional
public class ObjectsServiceImpl implements ObjectsService{


    @Autowired
    private CharacterDao characterDao;
    @Autowired
    private EventDao eventDao;
    @Autowired
    private LineTimeDao lineTimeDao;
    @Autowired
    private PlotDao plotDao;
    @Autowired
    private EstructureService estructureService;
    


    //CHARACTERS

    @Override
    public Character addCharacter(Long projectId, String name, String bio, String mainImageUrl) throws DuplicateInstanceException, InstanceNotFoundException{

        Project project = estructureService.getProject(projectId);
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
    


    
    // EVENTS

    @Override
  public Event addEvent(Long timelineId, String title, String content, Integer chapterNumber, Integer chronoOrder) throws DuplicateInstanceException, InstanceNotFoundException{
       
        LineTime timeline = lineTimeDao.findById(timelineId).orElseThrow(() -> new InstanceNotFoundException("project.entities.timeline", timelineId));
        
        if (eventDao.findByLineTimeIdAndTitle(timeline.getId(), title) != null) {
            throw new DuplicateInstanceException("project.entities.event", title);
        }
        if (chapterNumber == null || chronoOrder == null || chapterNumber < 1 || chronoOrder < 1) {
            throw new IllegalArgumentException("Mensaje de error. Capítulo: " + chapterNumber + ", Orden: " + chronoOrder);
        }
        boolean isChapterOccupied = timeline.getEvents().stream()
                .anyMatch(event -> chapterNumber.equals(event.getChapterNumber()));
        if (isChapterOccupied) {
            throw new IllegalArgumentException("Ya existe un evento en el capítulo " + chapterNumber + ".");
        }
        boolean isChronoOccupied = timeline.getEvents().stream()
            .anyMatch(event -> chronoOrder.equals(event.getChronoOrder()));
        if (isChronoOccupied) {
            throw new IllegalArgumentException("Ya existe un evento en el orden cronométrico " + chronoOrder + ".");
        }
        
        
        Event event = new Event();
        event.setTitle(title);
        event.setContent(content);
        event.setChapterNumber(chapterNumber);
        event.setChronoOrder(chronoOrder);
        event.setLineTime(timeline); 
        
        eventDao.save(event);

        timeline.getEvents().add(event);

        return event;
    }
   @Override
    public Event modifyEvent(Long eventId, String title, String content, Integer chapterNumber, Integer chronoOrder) throws InstanceNotFoundException, DuplicateInstanceException, IllegalArgumentException{
    
        Event event = eventDao.findById(eventId)
            .orElseThrow(() -> new InstanceNotFoundException("project.entities.event", eventId));

        var currentTimeline = event.getLineTime();

        if (eventDao.findByLineTimeIdAndTitle(currentTimeline.getId(), title) != null) {
            throw new DuplicateInstanceException("project.entities.event", title);
        }
    
        if (chapterNumber == null || chronoOrder == null || chapterNumber < 1 || chronoOrder < 1) {
             throw new IllegalArgumentException("Datos incorrectos para project.entities.event. Capítulo: " + chapterNumber + ", Orden: " + chronoOrder);
        }
    
        boolean isChapterOccupied = currentTimeline.getEvents().stream()
                .anyMatch(e -> chapterNumber.equals(e.getChapterNumber()));
        if (isChapterOccupied && event.getChapterNumber() != chapterNumber) {
            throw new IllegalArgumentException("Ya existe un evento en el capítulo " + chapterNumber + ".");
        }

    
        boolean isChronoOccupied = currentTimeline.getEvents().stream()
             .anyMatch(e -> chronoOrder.equals(e.getChronoOrder()));
        if (isChronoOccupied && event.getChronoOrder() != chronoOrder) {
            throw new IllegalArgumentException("Ya existe un evento en el orden cronométrico " + chronoOrder + ".");
        }
    
        event.setTitle(title);
        event.setContent(content);
        event.setChapterNumber(chapterNumber);
        event.setChronoOrder(chronoOrder);
    
        return eventDao.save(event);
    }

    @Override
    public void deleteEvent(Long eventId) throws InstanceNotFoundException{

        Event event = eventDao.findById(eventId).orElseThrow(() -> new InstanceNotFoundException("project.entities.event", eventId));
        LineTime timeline = event.getLineTime();
        timeline.getEvents().remove(event);
        eventDao.deleteById(eventId);
        
    }

    // LINE TIMES


    @Override
    public LineTime getLineTimeByPlot(Long plotId) throws InstanceNotFoundException{
        Plot plot = plotDao.findById(plotId).orElseThrow(() -> new InstanceNotFoundException("project.entities.plot", plotId));
        return plot.getTimeline();
    }

    @Override
    public Block<Event> findEventsByName(Long timelineId, String title) throws InstanceNotFoundException {
    
        org.springframework.data.domain.Pageable pageable = PageRequest.of(0, 10);
    
        Slice<Event> slice = eventDao.findEventsByLineTimeAndName(timelineId, title, pageable);
    
        return new Block<>(slice.getContent(), slice.hasNext());
    }
}

   



    

