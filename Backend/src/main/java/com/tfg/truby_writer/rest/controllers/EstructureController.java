package com.tfg.truby_writer.backend.rest.controllers;

import com.tfg.truby_writer.backend.rest.dtos.Estructure.*;
import com.tfg.truby_writer.model.entities.*;
import com.tfg.truby_writer.model.enums.Enums;
import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;
import com.tfg.truby_writer.model.exceptions.ProjectPermissionException;
import com.tfg.truby_writer.model.services.Block;
import com.tfg.truby_writer.model.services.EstructureService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/structure")
public class EstructureController {

    private final EstructureService estructureService;

    public EstructureController(EstructureService estructureService) {
        this.estructureService = estructureService;
    }

    // PROJECT 

    @PostMapping("/projects/createProject")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createProject(@RequestAttribute("userId") Long userId, @Validated @RequestBody ProjectDto dto) 
            throws DuplicateInstanceException, InstanceNotFoundException {
    
        User user = User.builder().id(userId).build(); 
    
        Project project = estructureService.createProject(user, dto.getName(), dto.getDescription());
        return ProjectConversor.toProjectDto(project);
    }

    @GetMapping("/projects/{id}")
    public ProjectDto getProject(@PathVariable Long id) throws InstanceNotFoundException {
        Project project = estructureService.getProject(id);
        return ProjectConversor.toProjectDto(project);
    }

    @DeleteMapping("/projects/deleteProject/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@RequestAttribute("userId") Long userId, @PathVariable Long id) 
            throws InstanceNotFoundException, ProjectPermissionException {
        
        User user = User.builder().id(userId).build();
    
        estructureService.deleteProject(user, id);
       }

    // PLOT 

    @PostMapping("/projects/{projectId}/createPlot")
    @ResponseStatus(HttpStatus.CREATED)
    public PlotDto addPlot(@PathVariable Long projectId, @Valid @RequestBody PlotDto dto) 
            throws DuplicateInstanceException, InstanceNotFoundException {
        Plot plot = estructureService.addPlot(projectId, dto.getName(), dto.getDramaticSituation());
        return PlotConversor.toPlotDto(plot);
    }

    @PutMapping("/plots/modify/{plotId}")
    public PlotDto modifyPlot(@PathVariable Long plotId, @Valid @RequestBody PlotDto dto) throws InstanceNotFoundException {
        Plot plot = estructureService.modifyPlot(
                plotId, 
                dto.getName(), 
                dto.getDramaticSituation(), 
                dto.getStructWeaknessNeed(),
                dto.getStructDesire(), 
                dto.getStructAdversary(), 
                dto.getStructPlan(), 
                dto.getStructStruggle(), 
                dto.getStructSelfRevelation(), 
                dto.getStructNewEquilibrium()
        );
        return PlotConversor.toPlotDto(plot);
    }

    @DeleteMapping("/plots/delete/{plotId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlot(@PathVariable Long plotId) throws InstanceNotFoundException {
        estructureService.deletePlot(plotId);
    }

    @GetMapping("/plots/{plotId}")
    public PlotDto findPlotById(@PathVariable Long plotId) throws InstanceNotFoundException {
        Plot plot = estructureService.findPlotById(plotId);
        return PlotConversor.toPlotDto(plot);
    }

    // PREMISE 

    @PostMapping("/plots/{plotId}/createPremise")
    @ResponseStatus(HttpStatus.CREATED)
    public PremiseDto addPremise(@PathVariable Long plotId, @Valid @RequestBody PremiseDto dto) 
            throws DuplicateInstanceException, InstanceNotFoundException {
        Premise premise = estructureService.addPremise(plotId, dto.getPremise());
        return PremiseConversor.toPremiseDto(premise);
    }

    @PutMapping("/premises/modify/{premiseId}")
    public PremiseDto modifyPremise(@PathVariable Long premiseId, @Valid @RequestBody PremiseDto dto) throws InstanceNotFoundException {
        Premise premise = estructureService.modifyPremise(
                premiseId, 
                dto.getPremise(), 
                dto.getNarrativePossibilities(), 
                dto.getNarrativeChallenges(),
                dto.getProblems(), 
                dto.getFoundingPrinciple(), 
                dto.getConflict(), 
                dto.getMoralDecision()
        );
        return PremiseConversor.toPremiseDto(premise);
    }

    @DeleteMapping("/premises/delete/{premiseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePremise(@PathVariable Long premiseId) throws InstanceNotFoundException {
        estructureService.deletePremise(premiseId);
    }

    @GetMapping("/premises/findPremise/{premiseId}")
    public PremiseDto findPremiseById(@PathVariable Long premiseId) throws InstanceNotFoundException {
        Premise premise = estructureService.findPremiseById(premiseId);
        return PremiseConversor.toPremiseDto(premise);
    }

    @GetMapping("/plots/{plotId}/premises/search")
    public Block<PremiseDto> findPremisesByFilterName(@PathVariable Long plotId, @RequestParam String name) throws InstanceNotFoundException {
        Block<Premise> premiseBlock = estructureService.findPremisesByFilterName(plotId, name);
        return PremiseConversor.toPremiseBlockDto(premiseBlock);
    }

    // NETWORK NODES & RELATIONSHIPS 

    @PostMapping("/plots/{plotId}/network/addCharacter")
    @ResponseStatus(HttpStatus.CREATED)
    public NetworkNodeDto addCharacterToNetwork(@PathVariable Long plotId, @Valid @RequestBody NetworkNodeDto dto) 
            throws DuplicateInstanceException, InstanceNotFoundException {
        Enums.Role roleEnum = Enums.Role.valueOf(dto.getRole());
        NetworkNode node = estructureService.addCharactertoNetwork(plotId, dto.getCharacterId(), roleEnum);
        return NetworkConversor.toNetworkNodeDto(node);
    }

    @PutMapping("/plots/{plotId}/network/modifyCharacter/{characterId}")
    public NetworkNodeDto modifyCharacterRoleInNetwork(@PathVariable Long plotId, @PathVariable Long characterId, @Valid @RequestBody NetworkNodeDto dto) 
            throws InstanceNotFoundException {
        Enums.Role roleEnum = Enums.Role.valueOf(dto.getRole());
        NetworkNode node = estructureService.modifyCharacterRoleInNetwork(plotId, characterId, roleEnum);
        return NetworkConversor.toNetworkNodeDto(node);
    }

    @DeleteMapping("/plots/{plotId}/network/characters/deleteCharacter/{characterId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharacterFromNetwork(@PathVariable Long plotId, @PathVariable Long characterId) throws InstanceNotFoundException {
        estructureService.deleteCharacterfromNetwork(plotId, characterId);
    }

    @PostMapping("/plots/{plotId}/network/relationships")
    @ResponseStatus(HttpStatus.CREATED)
    public NetworkRelationshipDto addRelationshipToNetwork(@PathVariable Long plotId, @Valid @RequestBody NetworkRelationshipDto dto) 
            throws InstanceNotFoundException, DuplicateInstanceException {
        Enums.RelationshipType relationEnum = Enums.RelationshipType.valueOf(dto.getRelationship());
        NetworkRelationship relationship = estructureService.addRelationshiptoNetwork(
                plotId, 
                dto.getNodeFromId(), 
                dto.getNodeToId(), 
                relationEnum
        );
        return NetworkConversor.toNetworkRelationshipDto(relationship);
    }

    @PutMapping("/plots/{plotId}/network/relationships")
    public NetworkRelationshipDto modifyRelationship(@PathVariable Long plotId, @Valid @RequestBody NetworkRelationshipDto dto) throws InstanceNotFoundException {
        Enums.RelationshipType relationEnum = Enums.RelationshipType.valueOf(dto.getRelationship());
        NetworkRelationship relationship = estructureService.modifyRelationship(
                plotId, 
                dto.getNodeFromId(), 
                dto.getNodeToId(), 
                relationEnum
        );
        return NetworkConversor.toNetworkRelationshipDto(relationship);
    }

    @DeleteMapping("/plots/{plotId}/network/relationships")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRelationship(@PathVariable Long plotId, @RequestParam Long characterId1, @RequestParam Long characterId2) throws InstanceNotFoundException {
        estructureService.deleteRelationship(plotId, characterId1, characterId2);
    }
}