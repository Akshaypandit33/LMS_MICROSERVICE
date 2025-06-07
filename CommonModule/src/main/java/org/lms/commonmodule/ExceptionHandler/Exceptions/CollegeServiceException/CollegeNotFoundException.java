package org.lms.commonmodule.ExceptionHandler.Exceptions.CollegeServiceException;

public class CollegeNotFoundException extends RuntimeException{
    public CollegeNotFoundException(String message)
    {
        super(message);
    }
}
