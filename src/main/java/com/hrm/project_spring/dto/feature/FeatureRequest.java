package com.hrm.project_spring.dto.feature;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeatureRequest {
    @NotBlank(message = "Feature code is required")
    private String code;        // ví dụ: "EXAM"

    @NotBlank(message = "Feature name is required")
    private String name;        // ví dụ: "Quản lý kỳ thi"

    private String description;
}
