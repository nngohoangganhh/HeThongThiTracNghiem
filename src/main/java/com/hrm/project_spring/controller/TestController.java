package com.hrm.project_spring.controller;

import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.dto.test.AssignQuestionsRequest;
import com.hrm.project_spring.dto.test.TestRequest;
import com.hrm.project_spring.dto.test.TestResponse;
import com.hrm.project_spring.service.TestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tests")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PreAuthorize("hasAuthority('TEST:READ')")
    @GetMapping
    public ResponseEntity<PageResponse<TestResponse>> getAllTests(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(testService.getAllTest(pageNo, pageSize));
    }

    @PreAuthorize("hasAuthority('TEST:READ')")
    @GetMapping("/{id}")
    public ResponseEntity<TestResponse> getTestById(@PathVariable Long id) {
        return ResponseEntity.ok(testService.getTestById(id));
    }

    @PreAuthorize("hasAuthority('TEST:CREATE')")
    @PostMapping
    public ResponseEntity<TestResponse> createTest(@RequestBody TestRequest request) {
        return ResponseEntity.ok(testService.createTest(request));
    }

    @PreAuthorize("hasAuthority('TEST:UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<TestResponse> updateTest(@PathVariable Long id, @RequestBody TestRequest request) {
        return ResponseEntity.ok(testService.updateTest(id, request));
    }

    @PreAuthorize("hasAuthority('TEST:DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasAuthority('TEST:UPDATE')")
    @PostMapping("/{testId}/questions")
    public ResponseEntity<TestResponse> assignQuestions(
            @PathVariable Long testId,
            @Valid @RequestBody AssignQuestionsRequest request) {
        return ResponseEntity.ok(testService.assignQuestions(testId, request));
    }
}
