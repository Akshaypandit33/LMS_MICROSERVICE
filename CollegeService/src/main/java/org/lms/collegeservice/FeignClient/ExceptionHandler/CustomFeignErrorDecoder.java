package org.lms.collegeservice.FeignClient.ExceptionHandler;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        String message = "Unknown error";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().asInputStream()))) {
            // Read response body as String (error message)
            message = reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            // Ignore, fallback to default message
        }

        HttpStatus status;
        try {
            status = HttpStatus.valueOf(response.status());
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        // Throw centralized exception with status and message from remote service
        return new FeignServiceException(status, message);
    }
}