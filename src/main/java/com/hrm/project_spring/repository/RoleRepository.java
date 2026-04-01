package com.hrm.project_spring.repository;

import com.hrm.project_spring.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);
    Optional<Role> findByCode(String code);
    boolean existsByCode(String code);
}
