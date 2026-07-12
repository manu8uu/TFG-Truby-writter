package com.tfg.truby_writer.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.truby_writer.model.entities.LineTime;
import com.tfg.truby_writer.model.entities.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;
import java.util.List;


public interface LineTimeDao extends JpaRepository<LineTime, Long> {
    

    @Query("SELECT l FROM LineTime l WHERE " +
           "(:timelineId IS NULL OR l.id = :timelineId)")
    Slice<LineTime> findByTimelineAndChronological(
            @Param("timelineId") Long timelineId, 
            Pageable pageable);

    

}