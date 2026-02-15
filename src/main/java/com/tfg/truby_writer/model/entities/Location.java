package com.tfg.truby_writer.model.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "location")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "background_history", columnDefinition = "TEXT")
    private String backgroundHistory;

    @Column(name = "background_image_url", length = 500)
    private String backgroundImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plot_id", nullable = false)
    @ToString.Exclude
    private Plot plot;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<LocationPoint> points;
}