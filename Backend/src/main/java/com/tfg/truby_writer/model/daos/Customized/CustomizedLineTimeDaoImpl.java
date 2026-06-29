package com.tfg.truby_writer.model.daos.Customized;

import java.util.ArrayList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import com.tfg.truby_writer.model.entities.LineTime;

// 1. Es vital añadir el "implements"
public class CustomizedLineTimeDaoImpl implements CustomizedLineTimeDao {

    // 2. Sobrescribir el método exigido por la interfaz
    @Override
    public Slice<LineTime> find(Long timelineId, Boolean isChronological, int page, int size) {
        
        // Retorno provisional seguro para que Spring Boot no colapse
        return new SliceImpl<>(new ArrayList<>(), PageRequest.of(page, size), false);
    }
}