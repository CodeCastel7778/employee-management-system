package com.learn.employee_service.controller;

import com.learn.employee_service.dto.ApiResponse;
import com.learn.employee_service.dto.EmployeeDto;
import com.learn.employee_service.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeDto>> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        ApiResponse<EmployeeDto> apiResponse = ApiResponse.success(savedEmployee, HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> getEmployeeByEmployeeId(@PathVariable(name = "id") Long employeeId) {
        EmployeeDto employeeById = employeeService.getEmployeeById(employeeId);
        ApiResponse<EmployeeDto> apiResponse = ApiResponse.success(employeeById, HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> getAllEmployees() {
        List<EmployeeDto> getAllEmployees = employeeService.getAllEmployees();
        ApiResponse<List<EmployeeDto>> apiResponse = ApiResponse.success(getAllEmployees, HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> updateEmployee(@PathVariable(name = "id") Long employeeId,
                                                                   @Valid @RequestBody EmployeeDto updatedEmployee) {
        EmployeeDto employeeDto = employeeService.updateEmployee(employeeId, updatedEmployee);
        ApiResponse<EmployeeDto> apiResponse = ApiResponse.success(employeeDto, HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable(name = "id") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        ApiResponse<String> apiResponse = ApiResponse.success("Employee deleted successfully!", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
