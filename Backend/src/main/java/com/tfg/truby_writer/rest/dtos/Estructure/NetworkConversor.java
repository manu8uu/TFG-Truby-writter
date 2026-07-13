package com.tfg.truby_writer.backend.rest.dtos.Estructure;

import com.tfg.truby_writer.model.entities.NetworkNode;
import com.tfg.truby_writer.model.entities.NetworkRelationship;

public final class NetworkConversor {

    private NetworkConversor() {}

    public static NetworkNodeDto toNetworkNodeDto(NetworkNode node) {
        if (node == null) return null;
        return NetworkNodeDto.builder()
                .id(node.getId())
                .role(node.getRole() != null ? node.getRole().name() : null)
                .networkId(node.getNetwork() != null ? node.getNetwork().getId() : null)
                .characterId(node.getCharacter() != null ? node.getCharacter().getId() : null)
                .build();
    }

    public static NetworkRelationshipDto toNetworkRelationshipDto(NetworkRelationship relation) {
        if (relation == null) return null;
        return NetworkRelationshipDto.builder()
                .id(relation.getId())
                .relationship(relation.getRelationship() != null ? relation.getRelationship().name() : null)
                .description(relation.getDescription())
                .networkId(relation.getNetwork() != null ? relation.getNetwork().getId() : null)
                .nodeFromId(relation.getNodeFrom() != null ? relation.getNodeFrom().getId() : null)
                .nodeToId(relation.getNodeTo() != null ? relation.getNodeTo().getId() : null)
                .build();
    }
}