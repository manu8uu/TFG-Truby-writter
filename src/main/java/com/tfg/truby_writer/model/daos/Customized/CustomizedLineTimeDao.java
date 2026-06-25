package com.tfg.truby_writer.model.daos.Customized;

import org.springframework.data.domain.Slice;
import com.tfg.truby_writer.model.entities.LineTime;

public interface CustomizedLineTimeDao {

    Slice<LineTime> find(Long timelineId, Boolean isChronological, int page, int size);
    
}
