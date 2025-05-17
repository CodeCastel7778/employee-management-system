package com.learn.employee_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmployeeDto(Long id,
                          @NotBlank(message = "Employee first name is mandatory or required")
                          String firstName,
                          @NotBlank(message = "Employee last name is mandatory or required")
                          String lastName,
                          @NotBlank(message = "Employee email address is mandatory or required")
                          @Email(message = "Email Id must be valid")
                          String email) {}
