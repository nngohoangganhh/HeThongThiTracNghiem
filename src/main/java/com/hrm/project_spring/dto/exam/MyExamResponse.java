package com.hrm.project_spring.dto.exam;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MyExamResponse {
    private Long examId;
    private String examName;
    private String examDescription;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private List<MyTestSummary> tests;

    @Data
    @Builder
    public static class MyTestSummary {
        private Long testId;
        private String testTitle;
        private Integer durationMinutes;
        private Integer totalScore;
        private Boolean alreadySubmitted;
        private Long lastAttemptId;
    }
}
