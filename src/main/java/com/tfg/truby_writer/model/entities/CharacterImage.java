package com.tfg.truby_writer.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "character_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    private String caption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    @ToString.Exclude
    private Character character;
}