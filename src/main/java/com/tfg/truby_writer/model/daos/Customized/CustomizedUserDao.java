package com.tfg.truby_writer.model.daos.Customized;

import org.springframework.data.domain.Slice;
import com.tfg.truby_writer.model.entities.User;


public interface CustomizedUserDao {

    Slice<User> find(Boolean blocked, String text, int page, int size);
    
}
