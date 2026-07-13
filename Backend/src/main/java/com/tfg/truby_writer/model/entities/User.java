package com.tfg.truby_writer.model.entities;

import jakarta.persistence.*;
import lombok.*;
import com.tfg.truby_writer.model.enums.Enums;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Enums.UserRole role;

    @Column(name = "blocked", nullable = false)
    private Boolean blocked;

}