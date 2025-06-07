package org.lms.academicstructureservice.Exception;

import org.lms.commonmodule.ExceptionHandler.ErrorResponse;
import org.lms.commonmodule.ExceptionHandler.Exceptions.AcademicServiceExceptions.*;
import org.lms.commonmodule.ExceptionHandler.Exceptions.BusinessException;
import org.lms.commonmodule.ExceptionHandler.Exceptions.ResourceAlreadyExistException;
import org.lms.commonmodule.ExceptionHandler.Exceptions.ResourceNotFoundException;
import org.lms.commonmodule.ExceptionHandler.GlobalErrorCode;
import org.lms.commonmodule.FeignException.FeignServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignServiceException.class)
    public ResponseEntity<?> handleFeignException(FeignServiceException ex)
    {
        ErrorResponse response = ErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode("FEIGN_ERROR")
                .status(ex.getStatus())
                .build();
        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ExceptionHandler(ProgramNotFoundException.class)
    public ResponseEntity<?> handleProgramNotFound(ProgramNotFoundException ex){
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .errorCode(GlobalErrorCode.PROGRAM_NOT_FOUND)
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ProgramAlreadyExists.class)
    public ResponseEntity<?> handleProgramAlreadyExistsException(ProgramAlreadyExists ex)
    {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode(GlobalErrorCode.PROGRAM_ALREADY_EXISTS)
                .status(HttpStatus.CONFLICT)
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<?> handleDepartmentNotFound(DepartmentNotFoundException ex){
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .errorCode(GlobalErrorCode.DEPARTMENT_NOT_FOUND)
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DepartmentAlreadyExists.class)
    public ResponseEntity<?> handleProgramAlreadyExistsException(DepartmentAlreadyExists ex)
    {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode(GlobalErrorCode.DEPARTMENT_ALREADY_EXISTS)
                .status(HttpStatus.CONFLICT)
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(BranchNotFoundException.class)
    public ResponseEntity<?> handleBranchNotFound(BranchNotFoundException ex){
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .errorCode(GlobalErrorCode.BRANCH_NOT_FOUND)
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BranchAlreadyExistsException.class)
    public ResponseEntity<?> handleProgramAlreadyExistsException(BranchAlreadyExistsException ex)
    {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode(GlobalErrorCode.BRANCH_ALREADY_EXISTS)
                .status(HttpStatus.CONFLICT)
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex)
    {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT)
                .errorMessage(ex.getMessage())
                .errorCode(GlobalErrorCode.BUSINESS_EXCEPTION)
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<?> handleMissingHeader(MissingRequestHeaderException ex)
    {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode("Missing Header")
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<?> handleResourceAlreadyExist(ResourceAlreadyExistException ex)
    {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode("Resource Already Exist")
                .status(HttpStatus.CONFLICT)
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex)
    {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode("Resource Not Found")
                .status(HttpStatus.NOT_FOUND)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex)
    {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode("Invalid Argument")
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
