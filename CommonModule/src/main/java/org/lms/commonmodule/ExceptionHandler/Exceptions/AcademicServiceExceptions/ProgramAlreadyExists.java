package org.lms.commonmodule.ExceptionHandler.Exceptions.AcademicServiceExceptions;

public class ProgramAlreadyExists extends RuntimeException{
    public ProgramAlreadyExists(String message)
    {
        super(message);
    }
}
