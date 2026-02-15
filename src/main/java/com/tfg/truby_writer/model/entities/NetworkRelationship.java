package com.tfg.truby_writer.model.entities;


import com.tfg.truby_writer.model.enums.Enums.RelationshipType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "network_relationships")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NetworkRelationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RelationshipType relationship;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "network_id", nullable = false)
    @ToString.Exclude
    private Network network;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_from_id", nullable = false)
    @ToString.Exclude
    private NetworkNode nodeFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_to_id", nullable = false)
    @ToString.Exclude
    private NetworkNode nodeTo;
}