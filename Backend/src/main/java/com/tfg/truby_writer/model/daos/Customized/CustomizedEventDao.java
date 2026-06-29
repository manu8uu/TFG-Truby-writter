package com.tfg.truby_writer.model.daos.Customized;

import org.springframework.data.domain.Slice;
import com.tfg.truby_writer.model.entities.Event;

public interface CustomizedEventDao {

    public Slice<Event> find(Long lineTimeId, String title, Integer chapterNumber, Integer chronoOrder, int page, int size);
}