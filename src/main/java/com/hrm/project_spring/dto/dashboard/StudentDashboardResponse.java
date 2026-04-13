package com.hrm.project_spring.dto.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDashboardResponse {
    private long totalAttempts;
    private long submittedAttempts;
    private Double averageScore;
    private long totalExamsAssigned;
}
