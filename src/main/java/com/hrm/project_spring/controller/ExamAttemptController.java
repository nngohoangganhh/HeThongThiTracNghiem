package com.hrm.project_spring.controller;

import com.hrm.project_spring.dto.common.ApiResponse;
import com.hrm.project_spring.dto.exam.ExamAttemptStart;
import com.hrm.project_spring.dto.result.AttemptSubmitRequest;
import com.hrm.project_spring.dto.exam.ExamAttemptSubmit;
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
    public ResponseEntity<ApiResponse<ExamAttemptStart>> startAttempt(
            @PathVariable Long testId,
            Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.<ExamAttemptStart>builder()
                .success(true)
                .status(200)
                .message("Bắt đầu bài thi thành công")
                .data(attemptService.startAttempt(testId, authentication.getName()))
                .build());
    }
    @PreAuthorize("hasAuthority('EXAM:SUBMIT')")
    @PostMapping("/{attemptId}/submit")
    public ResponseEntity<ApiResponse<ExamAttemptSubmit>> submitAttempt(
            @PathVariable Long attemptId,
            @Valid @RequestBody AttemptSubmitRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.<ExamAttemptSubmit>builder()
                .success(true)
                .status(200)
                .message("Nộp bài thành công")
                .data(attemptService.submitAttempt(attemptId, authentication.getName(), request))
                .build());
    }
}
