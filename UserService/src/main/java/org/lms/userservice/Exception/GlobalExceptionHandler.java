package org.lms.userservice.Exception;

import org.lms.commonmodule.ExceptionHandler.ErrorResponse;
import org.lms.commonmodule.ExceptionHandler.Exceptions.CreationException;
import org.lms.commonmodule.ExceptionHandler.Exceptions.UserServiceExceptions.EmailAlreadyExistException;
import org.lms.commonmodule.ExceptionHandler.Exceptions.UserServiceExceptions.UserNotFoundException;
import org.lms.commonmodule.ExceptionHandler.GlobalErrorCode;
import org.lms.commonmodule.FeignException.FeignServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(FeignServiceException.class)
    public ResponseEntity<String> handleFeignServiceException(FeignServiceException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex)
    {
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .errorCode(GlobalErrorCode.USER_NOT_FOUND)
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<?> handleProgramAlreadyExistsException(EmailAlreadyExistException ex)
    {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode(GlobalErrorCode.EMAIL_ALREADY_EXISTS)
                .status(HttpStatus.CONFLICT)
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(CreationException.class)
    public ResponseEntity<?> handleCreationException(CreationException ex)
    {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode("Creation Error")
                .status(HttpStatus.NOT_IMPLEMENTED)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(errorResponse);
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
}
