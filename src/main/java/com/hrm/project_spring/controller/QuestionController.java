package com.hrm.project_spring.controller;

import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.dto.question.QuestionRequest;
import com.hrm.project_spring.dto.question.QuestionResponse;
import com.hrm.project_spring.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PreAuthorize("hasAuthority('QUESTION:READ')")
    @GetMapping
    public ResponseEntity<PageResponse<QuestionResponse>> getAllQuestion(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(questionService.getAllQuestion(pageNo, pageSize));
    }

    @PreAuthorize("hasAuthority('QUESTION:READ')")
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    @PreAuthorize("hasAuthority('QUESTION:CREATE')")
    @PostMapping
    public ResponseEntity<QuestionResponse> create(@RequestBody QuestionRequest request) {
        return ResponseEntity.ok(questionService.create(request));
    }

    @PreAuthorize("hasAuthority('QUESTION:UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponse> update(
            @PathVariable Long id,
            @RequestBody QuestionRequest request) {
        return ResponseEntity.ok(questionService.update(id, request));
    }

    @PreAuthorize("hasAuthority('QUESTION:DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
