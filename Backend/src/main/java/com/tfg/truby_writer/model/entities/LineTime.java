package com.tfg.truby_writer.model.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "line_time")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "color_code", length = 10)
    private String colorCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plot_id", nullable = false)
    @ToString.Exclude
    private Plot plot;

    @OneToMany(mappedBy = "lineTime", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<Event> events = new ArrayList<>();
    
}
