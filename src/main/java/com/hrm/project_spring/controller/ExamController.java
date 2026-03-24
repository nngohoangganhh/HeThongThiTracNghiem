package com.hrm.project_spring.controller;


import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.dto.exam.ExamRequest;
import com.hrm.project_spring.dto.exam.ExamResponse;
import com.hrm.project_spring.entity.User;
import com.hrm.project_spring.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;


@RequestMapping("/api/exams")
@RestController
public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')") // Chỉ ADMIN và TEACHER mới có quyền truy cập
    @GetMapping
    public ResponseEntity<PageResponse<ExamResponse>> getAllExam(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(examService.getAllExam(pageNo, pageSize));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @GetMapping("/{id}")
    public ResponseEntity<ExamResponse> getExamById(@PathVariable Long id) {
        return ResponseEntity.ok(examService.getExamById(id));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping
    public ResponseEntity<ExamResponse> create(@RequestBody @Valid ExamRequest request) {
        return ResponseEntity.ok(examService.create(request));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ExamResponse> update(@PathVariable Long id, @RequestBody @Valid ExamRequest request) {
        return ResponseEntity.ok(examService.update(id, request));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }
    //gán student vào exam
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping("/{examId}/students/{studentId}")
    public ResponseEntity assignStudentsToExam(@PathVariable Long examId, @PathVariable Long studentId) {
        return ResponseEntity.ok(examService.assignStudentsToExam(examId, Collections.singleton(studentId)));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @GetMapping("/{examId}/students")
    public ResponseEntity<Set<User>> getStudentsByExamId(@PathVariable Long examId) {
        return ResponseEntity.ok(examService.getStudents(examId));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @DeleteMapping("/{examId}/students/{studentId}")
    public ResponseEntity<Void> removeStudentFromExam(@PathVariable Long examId, @PathVariable Long studentId) {
        examService.removeStudentFromExam(examId, studentId);
        return ResponseEntity.noContent().build();
    }
}



