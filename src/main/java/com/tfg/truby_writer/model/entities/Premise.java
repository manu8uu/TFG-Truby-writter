package com.tfg.truby_writer.model.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "premise")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Premise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String premise;

    @Column(name = "narrative_possibilities", columnDefinition = "TEXT")
    private String narrativePossibilities;

    @Column(name = "narrative_challenges", columnDefinition = "TEXT")
    private String narrativeChallenges;

    @Column(columnDefinition = "TEXT")
    private String problems;

    @Column(name = "founding_principle", columnDefinition = "TEXT")
    private String foundingPrinciple;

    @Column(columnDefinition = "TEXT")
    private String conflict;

    @Column(name = "moral_decision", columnDefinition = "TEXT")
    private String moralDecision;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @ToString.Exclude
    private Project project;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plot_id")
    @ToString.Exclude
    private Plot plot;
}