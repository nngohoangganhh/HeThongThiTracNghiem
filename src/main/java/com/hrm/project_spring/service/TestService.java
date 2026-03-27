package com.hrm.project_spring.service;

import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.dto.test.TestRequest;
import com.hrm.project_spring.dto.test.TestResponse;
import com.hrm.project_spring.entity.Exam;
import com.hrm.project_spring.entity.Test;
import com.hrm.project_spring.entity.User;
import com.hrm.project_spring.repository.ExamRepository;
import com.hrm.project_spring.repository.TestRepository;
import com.hrm.project_spring.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class TestService {

    private final TestRepository testRepository;
    private final ExamRepository examRepository;
    private final UserRepository userRepository;

    public TestService(TestRepository testRepository, ExamRepository examRepository, UserRepository userRepository) {
        this.testRepository = testRepository;
        this.examRepository = examRepository;
        this.userRepository = userRepository;
    }

    public PageResponse<TestResponse> getAllTest(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Test> page = testRepository.findAll(pageable);
        List<TestResponse> data = page.getContent()
                .stream()
                .map(this::mapToResponse)
                .toList();
        return PageResponse.<TestResponse>builder()
                .content(data)
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    public TestResponse getTestById(Long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Test not found"));
        return mapToResponse(test);
    }

    public TestResponse createTest(TestRequest request) {
        Exam exam = null;
        if (request.getExamId() != null) {
            exam = examRepository.findById(request.getExamId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam not found"));
        }
        User user = null;
        if (request.getCreatedById() != null) {
            user = userRepository.findById(request.getCreatedById())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        }
        Test test = Test.builder()
                .title(request.getTitle())
                .durationMinutes(request.getDurationMinutes())
                .totalScore(request.getTotalScore())
                .exam(exam)
                .createdBy(user)
                .createAt(LocalDateTime.now())
                .build();
        Test savedTest = testRepository.save(test);
        return mapToResponse(savedTest);
    }

    public TestResponse updateTest(Long id, TestRequest request) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Test not found"));

        if (request.getExamId() != null) {
             Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam not found"));
             test.setExam(exam);
        }
        test.setTitle(request.getTitle());
        test.setDurationMinutes(request.getDurationMinutes());
        test.setTotalScore(request.getTotalScore());

        Test updatedTest = testRepository.save(test);
        return mapToResponse(updatedTest);
    }

    public void deleteTest(Long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Test not found"));
        testRepository.delete(test);
    }

    public TestResponse mapToResponse(Test test) {
        return TestResponse.builder()
                .id(test.getId())
                .examId(test.getExam() != null ? test.getExam().getId() : null)
                .title(test.getTitle())
                .durationMinutes(test.getDurationMinutes())
                .totalScore(test.getTotalScore())
                .createdBy(test.getCreatedBy())
                .createAt(LocalTime.from(test.getCreateAt()))
                .build();
    }


}
