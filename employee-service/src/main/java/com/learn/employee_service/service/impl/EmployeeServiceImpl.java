package com.learn.employee_service.service.impl;

import com.learn.employee_service.dto.EmployeeDto;
import com.learn.employee_service.entity.Employee;
import com.learn.employee_service.exception.ResourceNotFoundException;
import com.learn.employee_service.mapper.EmployeeMapper;
import com.learn.employee_service.repository.EmployeeRepository;
import com.learn.employee_service.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found with Id: " + employeeId)
        );
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .toList();
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found with Id: " + updatedEmployee.id())
        );
        employee.setFirstName(updatedEmployee.firstName());
        employee.setLastName(updatedEmployee.lastName());
        employee.setEmail(updatedEmployee.email());
        Employee updateEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updateEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found with Id: " + employeeId)
        );
        employeeRepository.deleteById(employeeId);
    }

}
