package com.tfg.truby_writer.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.truby_writer.model.entities.NetworkRelationship;
import com.tfg.truby_writer.model.entities.NetworkNode;
 

public interface NetworkRelationshipDao extends JpaRepository<NetworkRelationship, Long> {

    NetworkRelationship findByNodeFromAndNodeTo(NetworkNode n1, NetworkNode n2);
    
}
