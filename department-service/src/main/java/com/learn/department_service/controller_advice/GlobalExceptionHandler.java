package com.learn.department_service.controller_advice;

import com.learn.department_service.dto.ApiResponse;
import com.learn.department_service.exception.ResourceNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleBadRequestException(BadRequestException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getFieldErrors().forEach((error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String rootCause = Optional.ofNullable(ex.getRootCause())
                .map(Throwable::getMessage)
                .orElse(ex.getMessage());

        String field = extractFieldFromDetail(rootCause);
        String value = extractValueFromDetail(rootCause);

        String message = "Duplicate entry"
                + (field != null ? " for field '" + field + "'" : "")
                + (value != null ? " with value '" + value + "'" : "")
                + ". Please use a different value.";

        return buildResponse(message, HttpStatus.BAD_REQUEST);
    }

    private String extractFieldFromDetail(String message) {
        Pattern fieldPattern = Pattern.compile("Key \\((.*?)\\)=");
        Matcher matcher = fieldPattern.matcher(message);
        return matcher.find() ? matcher.group(1) : null;
    }

    private String extractValueFromDetail(String message) {
        Pattern valuePattern = Pattern.compile("=\\((.*?)\\) already exists");
        Matcher matcher = valuePattern.matcher(message);
        return matcher.find() ? matcher.group(1) : null;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGlobalException(Exception exception) {
        return buildResponse("An unexpected error occurred: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ApiResponse<String>> buildResponse(String message, HttpStatus status) {
        ApiResponse<String> apiResponse = ApiResponse.failure(message, status.value());
        return new ResponseEntity<>(apiResponse, status);
    }

}

