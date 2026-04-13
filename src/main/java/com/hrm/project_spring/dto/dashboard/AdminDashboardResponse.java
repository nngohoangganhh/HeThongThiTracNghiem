package com.hrm.project_spring.dto.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminDashboardResponse {
    private long totalUsers;
    private long totalExams;
    private long totalQuestions;
    private long totalAttempts;
    private long openExams;
}
