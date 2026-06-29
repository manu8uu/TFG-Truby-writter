package com.tfg.truby_writer.model.entities;


import com.tfg.truby_writer.model.enums.Enums.MarkerType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "location_points")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "coord_x", nullable = false)
    private Float coordX;

    @Column(name = "coord_y", nullable = false)
    private Float coordY;

    @Enumerated(EnumType.STRING)
    @Column(name = "marker_type")
    private MarkerType markerType;

    @Column(name = "marker_icon", length = 50)
    private String markerIcon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    @ToString.Exclude
    private Location location;
}