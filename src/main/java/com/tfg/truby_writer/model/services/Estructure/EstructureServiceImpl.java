package com.tfg.truby_writer.model.services.Estructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.tfg.truby_writer.model.entities.Project;
import com.tfg.truby_writer.model.entities.Plot;
import com.tfg.truby_writer.model.entities.Premise;
import com.tfg.truby_writer.model.entities.Location;
import com.tfg.truby_writer.model.entities.Character;
import com.tfg.truby_writer.model.entities.Network;
import com.tfg.truby_writer.model.entities.NetworkNode;
import com.tfg.truby_writer.model.entities.NetworkRelationship;
import com.tfg.truby_writer.model.entities.LineTime;
import com.tfg.truby_writer.model.services.Block;

import com.tfg.truby_writer.model.daos.PlotDao;
import com.tfg.truby_writer.model.daos.PremiseDao;
import com.tfg.truby_writer.model.daos.LocationDao;
import com.tfg.truby_writer.model.daos.CharacterDao;
import com.tfg.truby_writer.model.daos.NetworkDao;
import com.tfg.truby_writer.model.daos.NetworkNodeDao;
import com.tfg.truby_writer.model.daos.NetworkRelationshipDao;
import com.tfg.truby_writer.model.daos.ProjectDao;



import com.tfg.truby_writer.model.enums.Enums;


import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class EstructureServiceImpl implements EstructureService{

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private PlotDao plotDao;
    @Autowired
    private PremiseDao premiseDao;

    @Autowired
    private CharacterDao characterDao;
    @Autowired
    private NetworkDao networkDao;
    @Autowired
    private NetworkNodeDao networkNodeDao;
    @Autowired
    private NetworkRelationshipDao networkRelationshipDao;
    


    @Override
    public Plot addPlot(Long projectId, String name, String dramaticSituation) 
            throws DuplicateInstanceException, InstanceNotFoundException {

        Project project = projectDao.findById(projectId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.project", projectId));
                
        if (plotDao.findByProjectIdAndName(projectId, name) != null) {
            throw new DuplicateInstanceException("project.entities.plot", name);
        }
        
        Plot plot = new Plot();
        plot.setName(name);
        plot.setDramaticSituation(dramaticSituation);
        plot.setProject(project);
        plot.setLocations(null); 

        Network network = new Network();
        network.setPlot(plot); 
        plot.setNetwork(network); 

        LineTime lineTime = new LineTime();
        lineTime.setPlot(plot);
        plot.setTimeline(lineTime);


        plotDao.save(plot);

        return plot;
    }

    @Override
    public Plot modifyPlot(Long plotId, String name, String dramaticSituation, String structWeaknessNeed, String structDesire, String structAdversary, String structPlan, String structStruggle, String structSelfRevelation, String structNewEquilibrium) throws InstanceNotFoundException{
       
        Plot plot = plotDao.findById(plotId).orElseThrow(() -> new InstanceNotFoundException("project.entities.plot", plotId));
       
        plot.setName(name);
        plot.setDramaticSituation(dramaticSituation);
        plot.setStructWeaknessNeed(structWeaknessNeed);
        plot.setStructDesire(structDesire);
        plot.setStructAdversary(structAdversary);
        plot.setStructPlan(structPlan);
        plot.setStructStruggle(structStruggle);
        plot.setStructSelfRevelation(structSelfRevelation);
        plot.setStructNewEquilibrium(structNewEquilibrium);
        plotDao.save(plot);
       
        return plot;
    }

    @Override
    public void deletePlot(Long plotId) throws InstanceNotFoundException{

        plotDao.findById(plotId).orElseThrow(() -> new InstanceNotFoundException("project.entities.plot", plotId));
        plotDao.deleteById(plotId);
    }

    @Override
    public Plot findPlotByName( Long projecId, String plotName) throws InstanceNotFoundException{
        projectDao.findById(projecId).orElseThrow(() -> new InstanceNotFoundException("project.entities.project", projecId));
        return plotDao.findByProjectIdAndName(projecId, plotName);


    }
    @Override
    public Plot findPlotById(Long plotId) throws InstanceNotFoundException{

        Plot plot = plotDao.findById(plotId).orElseThrow(() -> new InstanceNotFoundException("project.entities.plot", plotId));
        return plot;
    }

    // PREMISES

    @Override
    public Block<Premise> findPremisesByFilterName(Long plotId, String name) throws InstanceNotFoundException {

        Slice<Premise> slice = premiseDao.find(plotId,name, 0, 10);
        return new Block<>(slice.getContent(), slice.hasNext());
        
    }

    @Override
    public Premise addPremise(Long plotId, String name) throws DuplicateInstanceException, InstanceNotFoundException {

        Plot plot = findPlotById(plotId);
        Block<Premise> existingPremises = findPremisesByFilterName(plotId, name);
        if (existingPremises != null && existingPremises.getItems().size() > 0) {
            throw new DuplicateInstanceException("project.entities.premise", name);
        }
        Premise newPremise = Premise.builder()
                .plot(plot)
                .premise(name)
                .build();

        return premiseDao.save(newPremise);
    }

    @Override
    public Premise modifyPremise(Long premiseId, String name, String narrativePossibilities, String narrativeChallenges, String problems, String foundingPrinciple, String conflict, String moralDecision) throws InstanceNotFoundException{
        Premise premise = premiseDao.findById(premiseId).orElseThrow(() -> new InstanceNotFoundException("project.entities.premise", premiseId));
        premise.setPremise(name);
        premise.setNarrativePossibilities(narrativePossibilities);
        premise.setNarrativeChallenges(narrativeChallenges);
        premise.setProblems(problems);
        premise.setFoundingPrinciple(foundingPrinciple);
        premise.setConflict(conflict);
        premise.setMoralDecision(moralDecision);
        premiseDao.save(premise);
        return premise; 
    }

    @Override
    public void deletePremise(Long premiseId) throws InstanceNotFoundException{
        premiseDao.findById(premiseId).orElseThrow(() -> new InstanceNotFoundException("project.entities.premise", premiseId));
        premiseDao.deleteById(premiseId);
    }

    @Override
    public Premise findPremiseById(Long premiseId) throws InstanceNotFoundException{
        return premiseDao.findById(premiseId).orElseThrow(() -> new InstanceNotFoundException("project.entities.premise", premiseId));
    }


    // CHARACTER NETWORK
    
    @Override
    public NetworkNode findCharacterNetworkByCharacterId(Long networkId,Long characterId) throws InstanceNotFoundException{
        Character character = characterDao.findById(characterId).orElseThrow(() -> new InstanceNotFoundException("project.entities.character", characterId));
        Network network = networkDao.findById(networkId).orElseThrow(() -> new InstanceNotFoundException("project.entities.network", networkId));
        NetworkNode networkNode = networkNodeDao.findByNetworkIdAndCharacterId(network.getId(), character.getId());
        if (networkNode == null) {
            throw new InstanceNotFoundException("project.entities.networknode", characterId);
        }
        return networkNode;
    }

    @Override
    public NetworkNode addCharactertoNetwork(Long plotId, Long characterId, Enums.Role role) throws DuplicateInstanceException, InstanceNotFoundException{
      Plot plot = plotDao.findById(plotId).orElseThrow(() -> new InstanceNotFoundException("project.entities.plot", plotId));
      Project project = plot.getProject();
      Long projectId = project.getId();
      if (projectId == null) {
          throw new InstanceNotFoundException("project.entities.plot", plotId);
      }
      Character character = characterDao.findById(characterId).orElseThrow(() -> new InstanceNotFoundException("project.entities.character", characterId));
      if (projectId != character.getProject().getId()) {
          throw new InstanceNotFoundException("project.entities.character", characterId);
      }
      Network network = plot.getNetwork();
      if (network == null) {      
          throw new InstanceNotFoundException("project.entities.network", network.getId());
      }
      if (networkNodeDao.findByNetworkIdAndCharacterId(network.getId(), character.getId()) != null) {
          throw new DuplicateInstanceException("project.entities.networknode", character.getId());
      }
      NetworkNode nN = new NetworkNode();
      nN.setCharacter(character);
      nN.setNetwork(network);
      nN.setRole(role);
      networkNodeDao.save(nN);

      return nN;

    }

    @Override
    public NetworkNode modifyCharacterRoleInNetwork(Long plotId, Long characterId, Enums.Role role) throws InstanceNotFoundException{
        
        Network network = networkDao.findByPlotId(plotId);
        if (network == null) {
            throw new InstanceNotFoundException("project.entities.network", plotId);
        }
        Character character = characterDao.findById(characterId).orElseThrow(() -> new InstanceNotFoundException("project.entities.character", characterId));
        NetworkNode networkNode = findCharacterNetworkByCharacterId(network.getId(), character.getId());
        if (networkNode == null) {
            throw new InstanceNotFoundException("project.entities.networknode", characterId);
        }
        networkNode.setRole(role);
        networkNodeDao.save(networkNode);
       
         return networkNode;

    }

    @Override
    public void deleteCharacterfromNetwork(Long plotId, Long characterId) throws InstanceNotFoundException{
        
        plotDao.findById(plotId).orElseThrow(() -> new InstanceNotFoundException("project.entities.plot", plotId));
        Network network = networkDao.findByPlotId(plotId);
        Character character = characterDao.findById(characterId).orElseThrow(() -> new InstanceNotFoundException("project.entities.character", characterId));
        NetworkNode networkNode = findCharacterNetworkByCharacterId(network.getId(), character.getId());
        if (networkNode == null) {
            throw new InstanceNotFoundException("project.entities.networknode", characterId);
        }
       
        networkNodeDao.deleteById(networkNode.getId());
    }


    // NETWORK RELATIONSHIPS

    @Override
    public NetworkRelationship findRelationshipByNodeFromAndNodeTo(Long nodeFromId, Long nodeToId) throws InstanceNotFoundException{
        NetworkNode nodeFrom = networkNodeDao.findById(nodeFromId).orElseThrow(() -> new InstanceNotFoundException("project.entities.networknode", nodeFromId));
        NetworkNode nodeTo = networkNodeDao.findById(nodeToId).orElseThrow(() -> new InstanceNotFoundException("project.entities.networknode", nodeToId));
        NetworkRelationship relationship = networkRelationshipDao.findByNodeFromAndNodeTo(nodeFrom, nodeTo);
        if (relationship == null) {
            throw new InstanceNotFoundException("project.entities.networkrelationship", nodeFromId);
        }
        return relationship;
    }
@Override
    public NetworkRelationship addRelationshiptoNetwork(Long plotId, Long characterId1, Long characterId2, Enums.RelationshipType relationshipType) throws InstanceNotFoundException, DuplicateInstanceException{
        // CORRECCIÓN: Buscar la red por PlotId, no por su propia Primary Key
        Network network = networkDao.findByPlotId(plotId);
        if (network == null) {
            throw new InstanceNotFoundException("project.entities.network", plotId);
        }
        
        Character character1 = characterDao.findById(characterId1).orElseThrow(() -> new InstanceNotFoundException("project.entities.character", characterId1));
        Character character2 = characterDao.findById(characterId2).orElseThrow(() -> new InstanceNotFoundException("project.entities.character", characterId2));
        NetworkNode networkNode1 = findCharacterNetworkByCharacterId(network.getId(), character1.getId());
        NetworkNode networkNode2 = findCharacterNetworkByCharacterId(network.getId(), character2.getId());
        
        if (networkRelationshipDao.findByNodeFromAndNodeTo(networkNode1, networkNode2) != null) {
             throw new DuplicateInstanceException("project.entities.networkrelationship", characterId1);
        }
        NetworkRelationship relationship = new NetworkRelationship();
        relationship.setNetwork(network);
        relationship.setNodeFrom(networkNode1);
        relationship.setNodeTo(networkNode2);
        relationship.setRelationship(relationshipType);
        networkRelationshipDao.save(relationship);
        return relationship;
    }

    @Override
    public NetworkRelationship modifyRelationship(Long plotId, Long characterId1, Long characterId2, Enums.RelationshipType relationshipType) throws InstanceNotFoundException{
        // CORRECCIÓN: Usar plotId en lugar de projectId y buscar por findByPlotId
        Network network = networkDao.findByPlotId(plotId);
        if (network == null) {
            throw new InstanceNotFoundException("project.entities.network", plotId);
        }
        
        Character character1 = characterDao.findById(characterId1).orElseThrow(() -> new InstanceNotFoundException("project.entities.character", characterId1));
        Character character2 = characterDao.findById(characterId2).orElseThrow(() -> new InstanceNotFoundException("project.entities.character", characterId2));
        NetworkNode networkNode1 = findCharacterNetworkByCharacterId(network.getId(), character1.getId());
        NetworkNode networkNode2 = findCharacterNetworkByCharacterId(network.getId(), character2.getId());
        
        if (networkNode1 == null || networkNode2 == null) {
            throw new InstanceNotFoundException("project.entities.networknode", characterId1);
        }
        NetworkRelationship relationship = networkRelationshipDao.findByNodeFromAndNodeTo(networkNode1, networkNode2);
        if (relationship == null) {
            throw new InstanceNotFoundException("project.entities.networkrelationship", characterId1);
        }
        relationship.setRelationship(relationshipType);
        networkRelationshipDao.save(relationship);
        return relationship;
    }

    @Override
    public void deleteRelationship(Long plotId, Long characterId1, Long characterId2) throws InstanceNotFoundException{
        // CORRECCIÓN: Usar plotId en lugar de projectId y buscar por findByPlotId
        Network network = networkDao.findByPlotId(plotId);
        if (network == null) {
            throw new InstanceNotFoundException("project.entities.network", plotId);
        }
        
        Character character1 = characterDao.findById(characterId1).orElseThrow(() -> new InstanceNotFoundException("project.entities.character", characterId1));
        Character character2 = characterDao.findById(characterId2).orElseThrow(() -> new InstanceNotFoundException("project.entities.character", characterId2));
        NetworkNode networkNode1 = findCharacterNetworkByCharacterId(network.getId(), character1.getId());
        NetworkNode networkNode2 = findCharacterNetworkByCharacterId(network.getId(), character2.getId());
        
        if (networkNode1 == null || networkNode2 == null) {
            throw new InstanceNotFoundException("project.entities.networknode", characterId1);
        }
        NetworkRelationship relationship = networkRelationshipDao.findByNodeFromAndNodeTo(networkNode1, networkNode2);
        if (relationship == null) {
            throw new InstanceNotFoundException("project.entities.networkrelationship", characterId1);
        }
        networkRelationshipDao.deleteById(relationship.getId());
    }
}