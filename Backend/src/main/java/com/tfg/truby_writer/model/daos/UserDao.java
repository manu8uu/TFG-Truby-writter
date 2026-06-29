package com.tfg.truby_writer.model.daos;

import com.tfg.truby_writer.model.daos.Customized.CustomizedUserDao;
import com.tfg.truby_writer.model.entities.User;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long>, CustomizedUserDao {
	
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

}
