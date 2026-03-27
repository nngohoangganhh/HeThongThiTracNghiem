package com.hrm.project_spring.dto.exam;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ExamListResponse {
    private Long id;
    private String name;
    private LocalDate startTime;
    private LocalDate endTime;
    private String status;
}
