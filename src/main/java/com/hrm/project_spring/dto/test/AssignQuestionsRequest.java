package com.hrm.project_spring.dto.test;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class AssignQuestionsRequest {

    @NotEmpty(message = "Danh sách questionIds không được rỗng")
    private List<Long> questionIds;
}
