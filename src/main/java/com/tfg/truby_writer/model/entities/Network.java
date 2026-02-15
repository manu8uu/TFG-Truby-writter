package com.tfg.truby_writer.model.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "network")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Network {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "background_color", length = 10)
    private String backgroundColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plot_id", nullable = false)
    @ToString.Exclude
    private Plot plot;

    @OneToMany(mappedBy = "network", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<NetworkNode> nodes;

    @OneToMany(mappedBy = "network", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<NetworkRelationship> relationships;
}