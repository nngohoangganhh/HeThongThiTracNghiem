package com.hrm.project_spring.dto.question;

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

    // Nội dung phương án
    private String content;


    // Đáp án đúng
    private Boolean isCorrect;


    // Trọng số điểm
    // MCQ Multiple dùng
    private BigDecimal scoreWeight;


    // Thứ tự hiển thị
    private Integer orderNum;
}
