package com.hrm.project_spring.service;

import com.hrm.project_spring.dto.result.*;
import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.entity.*;
import com.hrm.project_spring.repository.ExamAttemptRepository;
import com.hrm.project_spring.repository.StudentAnswerRepository;
import com.hrm.project_spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExamResultService {

    private final ExamAttemptRepository   examAttemptRepository;
    private final StudentAnswerRepository studentAnswerRepository;
    private final UserRepository userRepository;

    public PageResponse<AttemptSummaryResponse> getMyAttempts(String username, int pageNo, int pageSize) {
        User user = findUserByUsername(username);
        Page<ExamAttempt> page = examAttemptRepository.findByUserId(
                user.getId(),
                PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "startTime"))
        );
        return toPageResponse(page);
    }
    public AttemptReviewResponse getMyAttemptReview(String username, Long attemptId) {
        User user = findUserByUsername(username);
        ExamAttempt attempt = examAttemptRepository.findByIdAndUserId(attemptId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Không tìm thấy lần thi này hoặc bạn không có quyền xem"));
        return buildReviewResponse(attempt);
    }
    public PageResponse<AttemptSummaryResponse> getAttemptsByTest(Long testId, int pageNo, int pageSize) {
        Page<ExamAttempt> page = examAttemptRepository.findByTestId(
                testId,
                PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "startTime"))
        );
        return toPageResponse(page);
    }

    public PageResponse<AttemptSummaryResponse> getAttemptsByExam(Long examId, int pageNo, int pageSize) {
        Page<ExamAttempt> page = examAttemptRepository.findByExamId(
                examId,
                PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "startTime"))
        );
        return toPageResponse(page);
    }
    public PageResponse<AttemptSummaryResponse> getAttemptsByUser(Long userId, int pageNo, int pageSize) {
        Page<ExamAttempt> page =examAttemptRepository.findByUserId(
                userId,
                PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "startTime"))
        );
        return toPageResponse(page);
    }

    public AttemptReviewResponse getAttemptReviewById(Long attemptId) {
        if (attemptId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ExamResult ID không hợp lệ");
        }
        ExamAttempt attempt = examAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Không tìm thấy result với id: " + attemptId));
        return buildReviewResponse(attempt);
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Tài khoản không tồn tại: " + username));
    }

    private PageResponse<AttemptSummaryResponse> toPageResponse(Page<ExamAttempt> page) {
        List<AttemptSummaryResponse> content = page.getContent()
                .stream()
                .map(this::toSummaryResponse)
                .collect(Collectors.toList());

        return PageResponse.<AttemptSummaryResponse>builder()
                .content(content)
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    private AttemptSummaryResponse toSummaryResponse(ExamAttempt attempt) {
        Test test = attempt.getTest();
        Exam exam = test.getExam();
        String status = attempt.getSubmitTime() == null ? "IN_PROGRESS" : "SUBMITTED";

        return AttemptSummaryResponse.builder()
                .id(attempt.getId())
                .testId(test.getId())
                .testTitle(test.getTitle())
                .examId(exam != null ? exam.getId() : null)
                .examName(exam != null ? exam.getName() : null)
                .startTime(attempt.getStartTime())
                .submitTime(attempt.getSubmitTime())
                .score(attempt.getScore())
                .totalCorrect(attempt.getTotalCorrect())
                .totalQuestions(test.getQuestions() != null ? test.getQuestions().size() : 0)
                .status(status)
                .build();
    }

    private AttemptReviewResponse buildReviewResponse(ExamAttempt attempt) {
        Test test = attempt.getTest();
        String status = attempt.getSubmitTime() == null ? "IN_PROGRESS" : "SUBMITTED";

        // Load câu trả lời với JOIN FETCH (tránh N+1)
        List<StudentAnswer> studentAnswers =
                studentAnswerRepository.findByAttemptIdWithDetails(attempt.getId());

        List<StudentAnswerReviewResponse> answerReviews = studentAnswers.stream()
                .map(sa -> {
                    Question question = sa.getQuestion();
                    Answer selected = sa.getSelectedAnswer();

                    // Tìm đáp án đúng trong set answers của câu hỏi
                    Answer correctAnswer = question.getAnswers().stream()
                            .filter(a -> Boolean.TRUE.equals(a.getIsCorrect()))
                            .findFirst()
                            .orElse(null);

                    return StudentAnswerReviewResponse.builder()
                            .questionId(question.getId())
                            .questionContent(question.getContent())
                            .selectedAnswerId(selected != null ? selected.getId() : null)
                            .selectedAnswerContent(selected != null ? selected.getContent() : null)
                            .correctAnswerId(correctAnswer != null ? correctAnswer.getId() : null)
                            .correctAnswerContent(correctAnswer != null ? correctAnswer.getContent() : null)
                            .isCorrect(sa.getIsCorrect())
                            .build();
                })
                .collect(Collectors.toList());

        return AttemptReviewResponse.builder()
                .id(attempt.getId())
                .userId(attempt.getUser().getId())
                .username(attempt.getUser().getUsername())
                .testId(test.getId())
                .testTitle(test.getTitle())
                .totalQuestions(test.getQuestions() != null ? test.getQuestions().size() : 0)
                .startTime(attempt.getStartTime())
                .submitTime(attempt.getSubmitTime())
                .score(attempt.getScore())
                .totalCorrect(attempt.getTotalCorrect())
                .status(status)
                .answers(answerReviews)
                .build();
    }
}

