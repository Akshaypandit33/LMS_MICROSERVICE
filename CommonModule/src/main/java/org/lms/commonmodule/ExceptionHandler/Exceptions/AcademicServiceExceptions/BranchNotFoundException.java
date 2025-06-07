package org.lms.commonmodule.ExceptionHandler.Exceptions.AcademicServiceExceptions;

public class BranchNotFoundException extends RuntimeException{
    public BranchNotFoundException(String message)
    {
        super(message);
    }
}
