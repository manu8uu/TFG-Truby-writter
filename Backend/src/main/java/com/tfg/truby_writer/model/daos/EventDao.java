package com.tfg.truby_writer.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.truby_writer.model.entities.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface EventDao extends JpaRepository<Event, Long> {

    Event findByLineTimeIdAndTitle(Long lineTimeId, String title);

    @Query("SELECT e FROM Event e JOIN e.lineTime lt WHERE " +
           "lt.id = :timelineId AND " +
           "(:title IS NULL OR :title = '' OR LOWER(e.title) LIKE LOWER(CONCAT('%', :title, '%')))")
    Slice<Event> findEventsByLineTimeAndName(
            @Param("timelineId") Long timelineId, 
            @Param("title") String title, 
            Pageable pageable);
    

}