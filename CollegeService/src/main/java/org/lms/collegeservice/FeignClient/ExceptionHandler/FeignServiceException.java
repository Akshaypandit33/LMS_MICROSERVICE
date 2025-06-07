package org.lms.collegeservice.FeignClient.ExceptionHandler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FeignServiceException extends RuntimeException {
    private final HttpStatus status;

    public FeignServiceException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}