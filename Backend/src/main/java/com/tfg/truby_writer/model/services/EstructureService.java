package com.tfg.truby_writer.model.services;


import com.tfg.truby_writer.model.entities.Plot;
import com.tfg.truby_writer.model.entities.Premise;
import com.tfg.truby_writer.model.entities.Project;
import com.tfg.truby_writer.model.services.Block; 
import com.tfg.truby_writer.model.entities.Network;
import com.tfg.truby_writer.model.entities.NetworkNode;
import com.tfg.truby_writer.model.entities.NetworkRelationship;
import com.tfg.truby_writer.model.entities.User;



import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException; 
import com.tfg.truby_writer.model.exceptions.ProjectPermissionException;
import com.tfg.truby_writer.model.exceptions.ProjectPermissionException;

import java.util.List;
import com.tfg.truby_writer.model.enums.Enums;



public interface EstructureService {

    //PROJECT

    Project createProject(User user, String name, String description) throws DuplicateInstanceException, InstanceNotFoundException;
    
    Project getProject(Long id) throws InstanceNotFoundException;
        
    void deleteProject(User user,Long id) throws InstanceNotFoundException, ProjectPermissionException;

    List<Project> getAllProjectsByUserId(Long userId) throws InstanceNotFoundException;



    // PLOTS

    Plot addPlot(Long projectId, String name, String dramaticSituation) throws DuplicateInstanceException, InstanceNotFoundException;

    Plot modifyPlot(Long plotId, String name, String dramaticSituation, String structWeaknessNeed, String structDesire, String structAdversary, String structPlan, String structStruggle, String structSelfRevelation, String structNewEquilibrium) throws InstanceNotFoundException;

    void deletePlot(Long plotId) throws InstanceNotFoundException;

    List<Plot> getAllPlotsByProjectId(Long projectId) throws InstanceNotFoundException;
    
    Plot findPlotByName( Long projectId, String name) throws InstanceNotFoundException;

    Plot findPlotById(Long plotId) throws InstanceNotFoundException;

    // PREMISES

    Premise addPremise(Long plotId, String premise) throws DuplicateInstanceException, InstanceNotFoundException;

    Premise modifyPremise(Long premiseId, String premise, String narrativePossibilities, String narrativeChallenges, String problems, String foundingPrinciple, String conflict, String moralDecision) throws InstanceNotFoundException;

    void deletePremise(Long premiseId) throws InstanceNotFoundException;

    Premise findPremiseById(Long premiseId) throws InstanceNotFoundException;

    Block<Premise> findPremisesByFilterName(Long plotId, String name) throws InstanceNotFoundException;

    // NETWORK NODES AND RELATIONSHIPS

    public NetworkNode findCharacterNetworkByCharacterId(Long networkId,Long characterId) throws InstanceNotFoundException;

    public NetworkNode addCharactertoNetwork(Long plotId, Long characterId, Enums.Role role) throws DuplicateInstanceException, InstanceNotFoundException;

    public NetworkNode modifyCharacterRoleInNetwork(Long plotId, Long characterId, Enums.Role role) throws InstanceNotFoundException;
    
    public void deleteCharacterfromNetwork(Long plotId, Long characterId) throws InstanceNotFoundException;

  
    public NetworkRelationship findRelationshipByNodeFromAndNodeTo(Long nodeFromId, Long nodeToId) throws InstanceNotFoundException;

    public NetworkRelationship addRelationshiptoNetwork(Long plotId, Long characterId1, Long characterId2, Enums.RelationshipType relationshipType) 
    throws InstanceNotFoundException, DuplicateInstanceException;

    public void deleteRelationship(Long plotId, Long characterId1, Long characterId2) throws InstanceNotFoundException;

    public NetworkRelationship modifyRelationship(Long plotId, Long characterId1, Long characterId2, Enums.RelationshipType relationshipType) throws InstanceNotFoundException;


}
