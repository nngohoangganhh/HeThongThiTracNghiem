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
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;      // ví dụ: "EXAM:READ"

    private String action;    // ví dụ: "READ", "CREATE", "UPDATE", "DELETE"
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "feature_id")
    private Feature feature;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();
}