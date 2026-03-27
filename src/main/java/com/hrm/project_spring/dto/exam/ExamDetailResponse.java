package com.hrm.project_spring.dto.exam;

import com.hrm.project_spring.dto.student.StudentResponse;
import com.hrm.project_spring.dto.user.UserResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamDetailResponse {
    private Long id;
    private String name;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long createdById;
    private String createdByUsername;
    private String status;
    private LocalTime createdAt;
    private UserResponseDto createdBy;
    private List<UserResponseDto> students;


}
