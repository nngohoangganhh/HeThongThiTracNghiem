package com.hrm.project_spring.dto.test;

import lombok.*;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestRequest {
    private Long examId;
    private String title;
    private Integer durationMinutes;
    private Integer totalScore;

    private LocalTime createAt;
}
