package com.learn.department_service.service.impl;

import com.learn.department_service.dto.DepartmentDto;
import com.learn.department_service.entity.Department;
import com.learn.department_service.exception.ResourceNotFoundException;
import com.learn.department_service.mapper.DepartmentMapper;
import com.learn.department_service.repository.DepartmentRepository;
import com.learn.department_service.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        Department department = DepartmentMapper.mapToDepartment(departmentDto);
        Department savedDepartment = departmentRepository.save(department);
        return DepartmentMapper.mapToDepartmentDto(savedDepartment);
    }

    @Override
    public DepartmentDto getDepartmentByCode(String code) {
        Department department = departmentRepository.findByDepartmentCode(code).orElseThrow(() ->
                new ResourceNotFoundException("Department not found by code: " + code)
        );
        return DepartmentMapper.mapToDepartmentDto(department);
    }

}
