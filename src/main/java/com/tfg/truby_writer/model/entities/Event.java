package com.tfg.truby_writer.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "chapter_number")
    private Integer chapterNumber;

    @Column(name = "chrono_order")
    private Integer chronoOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timeline_id", nullable = false)
    @ToString.Exclude
    private LineTime lineTime;
}