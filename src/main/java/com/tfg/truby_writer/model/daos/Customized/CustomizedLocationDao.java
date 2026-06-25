package com.tfg.truby_writer.model.daos.Customized;

import org.springframework.data.domain.Slice;
import com.tfg.truby_writer.model.entities.Location;

public interface CustomizedLocationDao {

    Slice<Location> find(Long plotId,String text, int page, int size);
    
}