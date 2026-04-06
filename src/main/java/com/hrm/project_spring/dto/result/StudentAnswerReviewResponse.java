package com.hrm.project_spring.dto.result;

import lombok.Builder;
import lombok.Data;

/**
 * Từng dòng đáp án trong phần review kết quả
 */
@Data
@Builder
public class StudentAnswerReviewResponse {
    private Long questionId;
    private String questionContent;
    private Long selectedAnswerId;      // Đáp án student đã chọn (null = bỏ trống)
    private String selectedAnswerContent;
    private Long correctAnswerId;       // Đáp án đúng
    private String correctAnswerContent;
    private Boolean isCorrect;
}
