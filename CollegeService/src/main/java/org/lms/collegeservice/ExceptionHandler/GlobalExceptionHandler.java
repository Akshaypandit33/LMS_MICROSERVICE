package org.lms.collegeservice.ExceptionHandler;

import org.lms.collegeservice.FeignClient.ExceptionHandler.FeignServiceException;
import org.lms.commonmodule.ExceptionHandler.ErrorResponse;
import org.lms.commonmodule.ExceptionHandler.Exceptions.CollegeServiceException.CollegeAlreadyExists;
import org.lms.commonmodule.ExceptionHandler.Exceptions.CollegeServiceException.CollegeNotFoundException;
import org.lms.commonmodule.ExceptionHandler.Exceptions.UserServiceExceptions.UserRoleAssignmentException;
import org.lms.commonmodule.ExceptionHandler.GlobalErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(FeignServiceException.class)
    public ResponseEntity<String> handleFeignServiceException(FeignServiceException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }
    @ExceptionHandler(CollegeNotFoundException.class)
    public ResponseEntity<?> handleCollegeNotFound(CollegeNotFoundException ex)
    {
        logger.warn("College not found {}",ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .errorCode(GlobalErrorCode.COLLEGE_NOT_FOUND)
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(UserRoleAssignmentException.class)
    public ResponseEntity<?> handleUserRoleAssignmentException(UserRoleAssignmentException ex)
    {
        logger.warn("Unable to assign user roles {}",ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_ACCEPTABLE)
                .errorCode(GlobalErrorCode.USER_ROLE_ASSIGNMENT_ERROR)
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(CollegeAlreadyExists.class)
    public ResponseEntity<?> handleCollegeAlreadyExist(CollegeAlreadyExists ex)
    {
        logger.warn("College already exists {}",ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT)
                .errorCode(GlobalErrorCode.COLLEGE_ALREADY_EXISTS)
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
}
