package com.hrm.project_spring.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttemptSubmitRequest {
    private List<StudentAnswerSubmitRequest> answers;
}
