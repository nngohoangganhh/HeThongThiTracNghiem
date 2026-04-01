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
    private Long id ;
    private String code;
    private String name;
    private String description;
    @OneToMany(mappedBy = "feature")
    private Set<Permission> permissions = new HashSet<>();

}
