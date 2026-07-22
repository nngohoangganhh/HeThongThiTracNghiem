package com.hrm.project_spring.dto.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentAllResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;

}
