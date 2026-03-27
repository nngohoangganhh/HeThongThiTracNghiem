package com.hrm.project_spring.dto.test;

import com.hrm.project_spring.entity.User;
import lombok.*;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestResponse {
    private Long id;

    private Long examId;

    private String title;

    private Integer durationMinutes;

    private Integer totalScore;

    private LocalTime createAt;
}
