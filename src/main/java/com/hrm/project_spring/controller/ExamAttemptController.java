package com.hrm.project_spring.controller;

import com.hrm.project_spring.dto.result.AttemptSubmitRequest;
import com.hrm.project_spring.dto.exam.ExamAttemptResponse;
import com.hrm.project_spring.service.ExamAttemptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attempts")
@RequiredArgsConstructor
public class ExamAttemptController {

    private final ExamAttemptService attemptService;

    @PreAuthorize("hasAuthority('EXAM:START')")
    @PostMapping("/start/test/{testId}")
    public ResponseEntity<ExamAttemptResponse> startAttempt(
            @PathVariable Long testId,
            Authentication authentication) {
        return ResponseEntity.ok(attemptService.startAttempt(testId, authentication.getName()));
    }
    @PreAuthorize("hasAuthority('EXAM:SUBMIT')")
    @PostMapping("/{attemptId}/submit")
    public ResponseEntity<ExamAttemptResponse> submitAttempt(
            @PathVariable Long attemptId,
            @Valid @RequestBody AttemptSubmitRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(attemptService.submitAttempt(attemptId, authentication.getName(), request));
    }
}
