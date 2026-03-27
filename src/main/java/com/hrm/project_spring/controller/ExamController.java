package com.hrm.project_spring.controller;

import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.dto.exam.ExamDetailResponse;
import com.hrm.project_spring.dto.exam.ExamListResponse;
import com.hrm.project_spring.dto.exam.ExamRequest;
import com.hrm.project_spring.dto.student.StudentResponse;
import com.hrm.project_spring.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/api/exams")
@RestController
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    // ======================== CRUD ========================

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @GetMapping
    public ResponseEntity<PageResponse<ExamListResponse>> getAllExam(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(examService.getAllExam(pageNo, pageSize));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @GetMapping("/{id}")
    public ResponseEntity<ExamDetailResponse> getExamById(@PathVariable Long id) {
        return ResponseEntity.ok(examService.getExamById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping
    public ResponseEntity<ExamDetailResponse> create(@RequestBody @Valid ExamRequest request) {
        return ResponseEntity.ok(examService.create(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ExamDetailResponse> update(@PathVariable Long id,
            @RequestBody @Valid ExamRequest request) {
        return ResponseEntity.ok(examService.update(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }

    // ======================== STUDENTS ========================

    // Lấy danh sách sinh viên của kỳ thi
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @GetMapping("/{examId}/students")
    public ResponseEntity<Set<StudentResponse>> getStudentsByExamId(@PathVariable Long examId) {
        return ResponseEntity.ok(examService.getStudentsByExamId(examId));
    }

    // Gán nhiều sinh viên vào kỳ thi (body: [1, 2, 3])
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping("/{examId}/students")
    public ResponseEntity<ExamDetailResponse> assignStudents(
            @PathVariable Long examId,
            @RequestBody Set<Long> studentIds) {
        return ResponseEntity.ok(examService.assignStudentsToExam(examId, studentIds));
    }

    // Xóa nhiều sinh viên khỏi kỳ thi (body: [1, 2, 3])
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @DeleteMapping("/{examId}/students")
    public ResponseEntity<ExamDetailResponse> removeStudents(
            @PathVariable Long examId,
            @RequestBody Set<Long> studentIds) {
        return ResponseEntity.ok(examService.removeStudentsFromExam(examId, studentIds));
    }
}
