package com.tfg.truby_writer.model.services.Events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;


import com.tfg.truby_writer.model.enums.Enums;
import com.tfg.truby_writer.model.services.Block;
import com.tfg.truby_writer.model.entities.Event;
import com.tfg.truby_writer.model.entities.LineTime;

import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException; 


public interface EventService {

    // EVENTS

    Event addEvent(Long timelineId, String name, String description, Integer chapterNumber, Integer chronoOrder) throws DuplicateInstanceException, InstanceNotFoundException;

    Event modifyEvent(Long eventId, String name, String description, Integer chapterNumber, Integer chronoOrder) throws InstanceNotFoundException;

    void deleteEvent(Long eventId) throws InstanceNotFoundException;

    Block<Event> findEventsByFilter(Long timelineId, String name, Integer chapterNumber, Integer chronoOrder) throws InstanceNotFoundException;

    // LINE TIMES

    Block<LineTime> findLineTimesByFilter(Long timelineId, Boolean isChronological) throws InstanceNotFoundException;

    LineTime getLineTimeByPlot(Long plotId) throws InstanceNotFoundException;
    
}
