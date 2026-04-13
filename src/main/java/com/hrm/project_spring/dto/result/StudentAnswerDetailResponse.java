package com.hrm.project_spring.dto.result;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentAnswerDetailResponse {
    private Long questionId;
    private String questionContent;
    private Long selectedAnswerId;
    private String selectedAnswerContent;
    private Long correctAnswerId;
    private String correctAnswerContent;
    private Boolean isCorrect;
}
