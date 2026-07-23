package com.hrm.project_spring.dto.question;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionOptionRequest {

    @NotBlank(message = "Nội dung phương án không được để trống.")
    @Size(max = 1000, message = "Nội dung phương án tối đa 1000 ký tự.")
    private String content;

    @NotNull(message = "Trạng thái đáp án đúng không được để trống.")
    private Boolean isCorrect;

    // Dùng cho MCQ Multiple
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "100.00")
    private BigDecimal score;
}
