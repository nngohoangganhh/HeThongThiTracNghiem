package com.hrm.project_spring.dto.question;

import com.hrm.project_spring.dto.answer.AnswerRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {
    private String content;
    private String questionType;
    private String difficulty;
}
