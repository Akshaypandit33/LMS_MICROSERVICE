package org.lms.commonmodule.FeignException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class FeignServiceException extends RuntimeException {
    private  HttpStatus status;

    public FeignServiceException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}