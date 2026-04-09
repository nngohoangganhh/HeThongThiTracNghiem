package com.hrm.project_spring.dto.exam;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ExamAttemptSubmit {
    private Long id;
    private Long userId;
    private Long testId;
    private LocalDateTime startTime;
    private LocalDateTime submitTime;
    private Double score;
    private Integer totalCorrect;
}
