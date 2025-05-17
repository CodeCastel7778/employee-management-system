package com.learn.department_service.controller;

import com.learn.department_service.dto.ApiResponse;
import com.learn.department_service.dto.DepartmentDto;
import com.learn.department_service.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentDto>> saveDepartment(@Valid @RequestBody DepartmentDto departmentDto) {
        DepartmentDto saveDepartment = departmentService.saveDepartment(departmentDto);
        ApiResponse<DepartmentDto> apiResponse = ApiResponse.success(saveDepartment, HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("{code}")
    public ResponseEntity<ApiResponse<DepartmentDto>> getDepartment(@PathVariable("code") String code) {
        DepartmentDto saveDepartment = departmentService.getDepartmentByCode(code);
        ApiResponse<DepartmentDto> apiResponse = ApiResponse.success(saveDepartment, HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
