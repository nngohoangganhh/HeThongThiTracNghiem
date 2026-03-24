package com.hrm.project_spring.repository;

import com.hrm.project_spring.entity.Role;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(@NotBlank(message = "role name is required") String name);
}
