package com.tfg.truby_writer.model.entities;

import com.tfg.truby_writer.model.enums.Enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "network_nodes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"network_id", "character_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NetworkNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('PROTAGONIST', 'PRINCIPAL', 'SUPPORTING_CHARACTER') DEFAULT 'PRINCIPAL'")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "network_id", nullable = false)
    @ToString.Exclude
    private Network network;

    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "character_id", nullable = false)
    @ToString.Exclude
    private Character character;
}