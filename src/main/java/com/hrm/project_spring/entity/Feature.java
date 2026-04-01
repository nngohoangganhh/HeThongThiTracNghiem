package com.hrm.project_spring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "features")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;        // ví dụ: "EXAM", "USER"

    private String name;
    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "feature", fetch = FetchType.LAZY)
    private Set<Permission> permissions = new HashSet<>();
}
