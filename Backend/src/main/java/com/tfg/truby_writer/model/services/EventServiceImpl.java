package com.tfg.truby_writer.model.services.Events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;


import com.tfg.truby_writer.model.services.Block;
import com.tfg.truby_writer.model.entities.Event;
import com.tfg.truby_writer.model.entities.LineTime;
import com.tfg.truby_writer.model.entities.Plot;


import com.tfg.truby_writer.model.daos.EventDao;
import com.tfg.truby_writer.model.daos.LineTimeDao;
import com.tfg.truby_writer.model.daos.PlotDao;

import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException; 

@Service
@Transactional
public class EventServiceImpl implements EventService{

    @Autowired
    private EventDao eventDao;
    @Autowired
    private LineTimeDao lineTimeDao;
    @Autowired
    private PlotDao plotDao;

    
    // EVENTS

  public Event addEvent(Long timelineId, String title, String content, Integer chapterNumber, Integer chronoOrder) throws DuplicateInstanceException, InstanceNotFoundException{
       
        LineTime timeline = lineTimeDao.findById(timelineId).orElseThrow(() -> new InstanceNotFoundException("project.entities.timeline", timelineId));
        
        if (eventDao.findByLineTimeIdAndTitle(timeline.getId(), title) != null) {
            throw new DuplicateInstanceException("project.entities.event", title);
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
    public Event modifyEvent(Long eventId, String title, String content, Integer chapterNumber, Integer chronoOrder) throws InstanceNotFoundException{
        Event event = eventDao.findById(eventId).orElseThrow(() -> new InstanceNotFoundException("project.entities.event", eventId));
        event.setTitle(title);
        event.setContent(content);
        event.setChapterNumber(chapterNumber);
        event.setChronoOrder(chronoOrder);
        eventDao.save(event);
        return event;
    }

    @Override
    public void deleteEvent(Long eventId) throws InstanceNotFoundException{
        Event event = eventDao.findById(eventId).orElseThrow(() -> new InstanceNotFoundException("project.entities.event", eventId));
        LineTime timeline = event.getLineTime();
        timeline.getEvents().remove(event);
        eventDao.deleteById(eventId);
    }

    @Override
    public Block<Event> findEventsByFilter(Long timelineId, String title, Integer chapterNumber, Integer chronoOrder) throws InstanceNotFoundException {

        Slice<Event> slice = eventDao.find(timelineId,title, chapterNumber, chronoOrder, 0, 10);
        return new Block<>(slice.getContent(), slice.hasNext());
        
    }

    // LINE TIMES

    @Override
    public Block<LineTime> findLineTimesByFilter(Long timelineId, Boolean isChronological) throws InstanceNotFoundException {

        Slice<LineTime> slice = lineTimeDao.find(timelineId, isChronological, 0, 10);
        return new Block<>(slice.getContent(), slice.hasNext());
        
    }

    @Override
    public LineTime getLineTimeByPlot(Long plotId) throws InstanceNotFoundException{
        Plot plot = plotDao.findById(plotId).orElseThrow(() -> new InstanceNotFoundException("project.entities.plot", plotId));
        return plot.getTimeline();
    }


}
    



    

