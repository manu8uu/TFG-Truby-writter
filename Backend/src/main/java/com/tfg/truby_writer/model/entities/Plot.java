package com.tfg.truby_writer.model.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "plot")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "dramatic_situation")
    private String dramaticSituation;

    // Estructura Narrativa (7 Pasos)
    @Column(name = "struct_weakness_need", columnDefinition = "TEXT")
    private String structWeaknessNeed;

    @Column(name = "struct_desire", columnDefinition = "TEXT")
    private String structDesire;

    @Column(name = "struct_adversary", columnDefinition = "TEXT")
    private String structAdversary;

    @Column(name = "struct_plan", columnDefinition = "TEXT")
    private String structPlan;

    @Column(name = "struct_struggle", columnDefinition = "TEXT")
    private String structStruggle;

    @Column(name = "struct_self_revelation", columnDefinition = "TEXT")
    private String structSelfRevelation;

    @Column(name = "struct_new_equilibrium", columnDefinition = "TEXT")
    private String structNewEquilibrium;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @ToString.Exclude
    private Project project;


    @OneToMany(mappedBy = "plot", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Location> locations;

    @OneToOne(mappedBy = "plot", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private LineTime timeline;

    @OneToOne(mappedBy = "plot", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Network network;
}