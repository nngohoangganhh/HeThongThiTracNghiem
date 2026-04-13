package com.hrm.project_spring.repository;

import com.hrm.project_spring.entity.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    Optional<Exam> findByIdWithStudents(@Param("id") Long id);

    // Lấy danh sách kỳ thi mà học sinh được gán (dùng cho /api/my-exams)
    @Query("SELECT e FROM Exam e JOIN e.students s WHERE s.id = :userId")
    Page<Exam> findByStudentId(@Param("userId") Long userId, Pageable pageable);

    // Đếm số kỳ thi đang OPEN (dùng cho admin dashboard)
    long countByStatus(String status);
}
