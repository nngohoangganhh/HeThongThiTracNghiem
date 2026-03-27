package com.hrm.project_spring.dto.exam;

import com.hrm.project_spring.dto.user.UserResponse;
import lombok.Data;

import java.util.List;

@Data
public class ExamAssign {
    private Long id;
    private String name;
    private List<UserResponse> students;
}
