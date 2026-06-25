package com.tfg.truby_writer.model.daos.Customized;

import org.springframework.data.domain.Slice;
import com.tfg.truby_writer.model.entities.Character;

public interface CustomizedCharacterDao {

    Slice<Character> find(Long projectId, String name, int page, int size);
    
}
