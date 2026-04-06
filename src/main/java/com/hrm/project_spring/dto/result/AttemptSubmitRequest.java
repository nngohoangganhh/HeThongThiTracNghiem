package com.hrm.project_spring.dto.result;

import lombok.Data;
import jakarta.validation.Valid;

import java.util.List;

@Data
public class AttemptSubmitRequest {
    @Valid
    private List<StudentAnswerSubmitRequest> answers;
}
