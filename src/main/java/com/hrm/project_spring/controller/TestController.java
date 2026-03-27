package com.hrm.project_spring.controller;

import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.dto.test.TestRequest;
import com.hrm.project_spring.dto.test.TestResponse;
import com.hrm.project_spring.service.TestService;
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
   @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')") // Chỉ ADMIN và TEACHER mới có quyền truy cập
    @GetMapping
    public ResponseEntity<PageResponse<TestResponse>> getAllTests(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(testService.getAllTest(pageNo, pageSize));
    }
     @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')") // Chỉ ADMIN và TEACHER mới có quyền truy cập
    @GetMapping("/{id}")
    public ResponseEntity<TestResponse> getTestById(@PathVariable Long id) {
        return ResponseEntity.ok(testService.getTestById(id));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')") // Chỉ ADMIN và TEACHER mới có quyền truy cập
    @PostMapping
    public ResponseEntity<TestResponse> createTest(@RequestBody TestRequest request) {
        return new ResponseEntity<>(testService.createTest(request), HttpStatus.CREATED);
    }
     @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')") // Chỉ ADMIN và TEACHER mới có quyền truy cập
    @PutMapping("/{id}")
    public ResponseEntity<TestResponse> updateTest(@PathVariable Long id, @RequestBody TestRequest request) {
        return ResponseEntity.ok(testService.updateTest(id, request));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')") // Chỉ ADMIN và TEACHER mới có quyền truy cập
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }
}
