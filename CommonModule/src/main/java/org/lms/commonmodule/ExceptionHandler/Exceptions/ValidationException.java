package org.lms.commonmodule.ExceptionHandler.Exceptions;

public class ValidationException extends RuntimeException{
    public ValidationException(String message)
    {
        super(message);
    }
}
