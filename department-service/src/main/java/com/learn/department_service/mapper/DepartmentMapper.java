package com.learn.department_service.mapper;

import com.learn.department_service.dto.DepartmentDto;
import com.learn.department_service.entity.Department;

public class DepartmentMapper {

    public static DepartmentDto mapToDepartmentDto(Department department) {
        return new DepartmentDto(
                department.getId(),
                department.getDepartmentName(),
                department.getDepartmentDescription(),
                department.getDepartmentCode()
        );
    }

    public static Department mapToDepartment(DepartmentDto departmentDto) {
        return new Department(
                departmentDto.id(),
                departmentDto.departmentName(),
                departmentDto.departmentDescription(),
                departmentDto.departmentCode()
        );
    }

}
