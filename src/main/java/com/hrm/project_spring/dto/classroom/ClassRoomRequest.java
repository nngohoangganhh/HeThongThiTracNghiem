package com.hrm.project_spring.dto.classroom;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClassRoomRequest {
    @NotBlank(message = "Mã lớp không được để trống")
    private String code;

    @NotBlank(message = "Tên lớp không được để trống")

    private String name;
    @Pattern(
            regexp = "^(20\\d{2})-(20\\d{2})$",
            message = "Năm học phải đúng định dạng YYYY-YYYY (ví dụ: 2025-2026)"
    )
    private String academicYear;

    private String description;
}
