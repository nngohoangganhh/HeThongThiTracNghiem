package com.hrm.project_spring.dto.user;

import lombok.Builder;
import lombok.Data;

import java.awt.*;

@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private String fullname;
}
