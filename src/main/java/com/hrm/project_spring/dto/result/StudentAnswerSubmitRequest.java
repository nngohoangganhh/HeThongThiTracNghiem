package com.hrm.project_spring.dto.result;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class StudentAnswerSubmitRequest {
    @NotNull(message = "Question ID không được để trống")
    private Long questionId;
    
    // selectedAnswerId có thể null nểu student bỏ trống (không chọn đáp án nào)
    private Long selectedAnswerId;
}
