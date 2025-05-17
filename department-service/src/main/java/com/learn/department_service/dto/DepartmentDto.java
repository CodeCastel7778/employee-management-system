package com.learn.department_service.dto;

import jakarta.validation.constraints.NotBlank;

public record DepartmentDto(
        Long id,
        @NotBlank(message = "Department name is mandatory or required")
        String departmentName,
        @NotBlank(message = "Department description is mandatory or required")
        String departmentDescription,
        @NotBlank(message = "Department code is mandatory or required")
        String departmentCode
) {}
