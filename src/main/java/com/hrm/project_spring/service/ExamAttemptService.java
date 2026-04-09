package com.hrm.project_spring.service;

import com.hrm.project_spring.dto.exam.ExamAttemptStart;
import com.hrm.project_spring.dto.result.AttemptSubmitRequest;
import com.hrm.project_spring.dto.exam.ExamAttemptSubmit;
import com.hrm.project_spring.dto.result.StudentAnswerSubmitRequest;
import com.hrm.project_spring.entity.*;
import com.hrm.project_spring.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExamAttemptService {

    private final ExamAttemptRepository attemptRepository;
    private final TestRepository testRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public ExamAttemptStart startAttempt(Long testId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, " tài khoản không tồn tại"));
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Test không tồn tại"));

        ExamAttempt attempt = ExamAttempt.builder()
                .user(user)
                .test(test)
                .startTime(LocalDateTime.now())
                .build();

        attempt = attemptRepository.save(attempt);
        return maptoStart(attempt);
    }

    @Transactional
    public ExamAttemptSubmit submitAttempt(Long attemptId, String username, AttemptSubmitRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, " tài khoản không tồn tại"));

        ExamAttempt attempt = attemptRepository.findByIdAndUserId(attemptId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, " bài thi không tồn tại "));
        // không cho nộp quá 1 lần
         if (attempt.getSubmitTime() != null) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST," bài thi đã được nộp");
         }
        int totalCorrect = 0;
        if (request.getAnswers() != null) {
            for (StudentAnswerSubmitRequest ansReq : request.getAnswers()) {
                Question question = questionRepository.findById(ansReq.getQuestionId())
                        .orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Câu hỏi không tồn tại"));
                boolean isCorrect = false;
                Answer selectedAnswer = null;
                if (ansReq.getSelectedAnswerId() != null) {
                    selectedAnswer = answerRepository
                            .findByIdAndQuestionId(ansReq.getSelectedAnswerId(), question.getId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                    "câu trả lời cho câu hỏi không phù hợp"));
                    isCorrect = Boolean.TRUE.equals(selectedAnswer.getIsCorrect());
                    if (isCorrect) {
                        totalCorrect++;
                    }
                }
                StudentAnswer studentAnswer = StudentAnswer.builder()
                        .attempt(attempt)
                        .question(question)
                        .selectedAnswer(selectedAnswer)
                        .isCorrect(isCorrect)
                        .build();
                attempt.addStudentAnswer(studentAnswer);
            }
        }
        attempt.setSubmitTime(LocalDateTime.now());
        attempt.setTotalCorrect(totalCorrect);
        int totalQuestions = attempt.getTest().getQuestions().size();
        Integer testTotalScore = attempt.getTest().getTotalScore();
        if (testTotalScore == null)
            testTotalScore = 10; // Default 10 points system
        if (totalQuestions > 0) {
            double rawScore = ((double) totalCorrect / totalQuestions) * testTotalScore;
            attempt.setScore((double) Math.round(rawScore * 100) / 100); // 2 decimal places
        } else {
            attempt.setScore(0.0);
        }
        attempt = attemptRepository.save(attempt);
        return mapToResponse(attempt);
    }

    private ExamAttemptSubmit mapToResponse(ExamAttempt attempt) {
        ExamAttemptSubmit dto = new ExamAttemptSubmit();
        dto.setId(attempt.getId());
        dto.setUserId(attempt.getUser().getId());
        dto.setTestId(attempt.getTest().getId());
        dto.setStartTime(attempt.getStartTime());
        dto.setSubmitTime(attempt.getSubmitTime());
        dto.setScore(attempt.getScore());
        dto.setTotalCorrect(attempt.getTotalCorrect());
        return dto;
    }

    private ExamAttemptStart maptoStart(ExamAttempt attempt) {
        ExamAttemptStart start = new ExamAttemptStart();
        start.setId(attempt.getId());
        start.setTestId(attempt.getTest().getId());
        start.setUserId(attempt.getUser().getId());
        start.setStartTime(attempt.getStartTime());
        return start;
    }
}
