package com.tfg.truby_writer.model.daos.Customized;

import org.springframework.data.domain.Slice;
import com.tfg.truby_writer.model.entities.Premise;


public interface CustomizedPremiseDao {

    Slice<Premise> find(Long plotId,String text, int page, int size);
    
}