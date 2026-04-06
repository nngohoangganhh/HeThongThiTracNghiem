package com.hrm.project_spring.controller;

import com.hrm.project_spring.dto.answer.AnswerRequest;
import com.hrm.project_spring.dto.answer.AnswerResponse;
import com.hrm.project_spring.service.AnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PreAuthorize("hasAuthority('QUESTION:READ')")
    @GetMapping
    public ResponseEntity<List<AnswerResponse>> getAnswersByQuestion(
            @PathVariable Long questionId,
            @RequestParam(defaultValue = "false") boolean isExamTime) {
        boolean includeIsCorrect = !isExamTime; 
        return ResponseEntity.ok(answerService.getAnswersByQuestionId(questionId, includeIsCorrect));
    }

    @PreAuthorize("hasAuthority('QUESTION:UPDATE')")
    @PostMapping
    public ResponseEntity<AnswerResponse> createAnswer(
            @PathVariable Long questionId,
            @Valid @RequestBody AnswerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(answerService.addAnswerToQuestion(questionId, request));
    }

    @PreAuthorize("hasAuthority('QUESTION:UPDATE')")
    @PostMapping("/bulk")
    public ResponseEntity<List<AnswerResponse>> createBulkAnswers(
            @PathVariable Long questionId,
            @Valid @RequestBody List<AnswerRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(answerService.addBulkAnswers(questionId, requests));
    }

    @PreAuthorize("hasAuthority('QUESTION:UPDATE')")
    @PutMapping("/{answerId}")
    public ResponseEntity<AnswerResponse> updateAnswer(
            @PathVariable Long questionId,
            @PathVariable Long answerId,
            @Valid @RequestBody AnswerRequest request) {
        return ResponseEntity.ok(answerService.updateAnswer(questionId, answerId, request));
    }

    @PreAuthorize("hasAuthority('QUESTION:DELETE')")
    @DeleteMapping("/{answerId}")
    public ResponseEntity<Void> deleteAnswer(
            @PathVariable Long questionId,
            @PathVariable Long answerId) {
        answerService.deleteAnswer(questionId, answerId);
        return ResponseEntity.noContent().build();
    }
}
