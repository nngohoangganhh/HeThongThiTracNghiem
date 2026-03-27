package com.hrm.project_spring.repository;

import com.hrm.project_spring.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
  @Query("""
    SELECT e FROM Exam e
    LEFT JOIN FETCH e.students
    LEFT JOIN FETCH e.createdBy
    WHERE e.id = :id
""")
  Optional<Exam> findByIdWithStudents(Long id);
}
