package com.hrm.project_spring.controller;

import com.hrm.project_spring.dto.result.AttemptReviewResponse;
import com.hrm.project_spring.dto.result.AttemptSummaryResponse;
import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.service.ExamResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ExamResultController {
    private final ExamResultService examResultService;
    @PreAuthorize("hasAuthority('RESULT:READ')")
    @GetMapping("/my")
    public ResponseEntity<PageResponse<AttemptSummaryResponse>> getMyAttempts(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize,
            Authentication authentication) {
        return ResponseEntity.ok( examResultService.getMyAttempts(authentication.getName(), pageNo, pageSize ));

    }
    @PreAuthorize("hasAuthority('RESULT:REVIEW')")
    @GetMapping("/my/{attemptId}/review")
    public ResponseEntity<AttemptReviewResponse> getMyAttemptReview(
            @PathVariable Long attemptId,
            Authentication authentication) {
        return ResponseEntity.ok(
               examResultService.getMyAttemptReview(authentication.getName(), attemptId)
        );
    }
    @PreAuthorize("hasAuthority('TEST:READ')")
    @GetMapping("/test/{testId}")
    public ResponseEntity<PageResponse<AttemptSummaryResponse>> getAttemptsByTest(
            @PathVariable Long testId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(examResultService.getAttemptsByTest(testId, pageNo, pageSize));
    }
    @PreAuthorize("hasAuthority('EXAM:READ')")
    @GetMapping("/exam/{examId}")
    public ResponseEntity<PageResponse<AttemptSummaryResponse>> getAttemptsByExam(
            @PathVariable Long examId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(examResultService.getAttemptsByExam(examId, pageNo, pageSize));
    }
    @PreAuthorize("hasAuthority('USER:READ')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<PageResponse<AttemptSummaryResponse>> getAttemptsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(examResultService.getAttemptsByUser(userId, pageNo, pageSize));
    }
    @PreAuthorize("hasAuthority('EXAM:READ')")
    @GetMapping("/{attemptId}/review")
    public ResponseEntity<AttemptReviewResponse> getAttemptReview(
            @PathVariable Long attemptId) {
        return ResponseEntity.ok(examResultService.getAttemptReviewById(attemptId));
    }
}

