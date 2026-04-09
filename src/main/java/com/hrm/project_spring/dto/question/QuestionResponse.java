package com.hrm.project_spring.dto.question;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {
    private Long id;
    private String content;
    private String questionType;
    private String difficulty;
    private String createdBy;
    private LocalDateTime createdAt;

}
