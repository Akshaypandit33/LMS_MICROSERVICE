package org.lms.commonmodule.ExceptionHandler.Exceptions.CollegeServiceException;

public class CollegeAlreadyExists extends RuntimeException{
    public CollegeAlreadyExists(String message)
    {
        super(message);
    }
}
