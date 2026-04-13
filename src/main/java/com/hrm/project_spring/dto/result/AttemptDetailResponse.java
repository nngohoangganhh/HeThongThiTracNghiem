package com.hrm.project_spring.dto.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AttemptDetailResponse {
    private Long id;
    private Long userId;
    private String username;
    private Long testId;
    private String testTitle;
    private LocalDateTime startTime;
    private LocalDateTime submitTime;
    private Double score;
    private Integer totalCorrect;
    private Integer totalQuestions;
    private List<StudentAnswerDetailResponse> answers;
}
