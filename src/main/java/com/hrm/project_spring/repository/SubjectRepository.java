package com.hrm.project_spring.repository;

import com.hrm.project_spring.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
