package com.hrm.project_spring.dto.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamResponse {
    private Long id;
    private String name;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long createdById;
    private String createdByUsername;
    private String status;
    private LocalTime createdAt;
}
