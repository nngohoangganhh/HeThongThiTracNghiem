package com.hrm.project_spring.dto.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AttemptSummaryResponse {
    private Long id;
    private Long testId;
    private String testTitle;
    private Long examId;
    private String examName;
    private LocalDateTime startTime;
    private LocalDateTime submitTime;
    private Double score;
    private Integer totalCorrect;
    private Integer totalQuestions;
    private Boolean submitted;
}
