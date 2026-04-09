package com.hrm.project_spring.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentAnswerSubmitRequest {
    private Long questionId;
    private Long selectedAnswerId;
}
