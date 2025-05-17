package com.learn.department_service.dto;

public record DepartmentDto(
        Long id,
        String departmentName,
        String departmentDescription,
        String departmentCode
) {}
