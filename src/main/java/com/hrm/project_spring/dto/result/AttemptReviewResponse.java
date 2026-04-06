package com.hrm.project_spring.dto.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AttemptReviewResponse {
    private Long id;
    private Long userId;
    private String username;
    private Long testId;
    private String testTitle;
    private Integer totalQuestions;
    private LocalDateTime startTime;
    private LocalDateTime submitTime;
    private Double score;
    private Integer totalCorrect;
    private String status; // "IN_PROGRESS" | "SUBMITTED"
    private List<StudentAnswerReviewResponse> answers;
}
