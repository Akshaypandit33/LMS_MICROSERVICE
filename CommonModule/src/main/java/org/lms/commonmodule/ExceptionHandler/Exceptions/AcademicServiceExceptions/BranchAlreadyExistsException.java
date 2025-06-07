package org.lms.commonmodule.ExceptionHandler.Exceptions.AcademicServiceExceptions;

public class BranchAlreadyExistsException extends RuntimeException{
    public BranchAlreadyExistsException(String message)
    {
        super(message);
    }
}
