package org.lms.commonmodule.ExceptionHandler.Exceptions.AcademicServiceExceptions;

public class DepartmentAlreadyExists extends RuntimeException{
    public DepartmentAlreadyExists(String message)
    {
        super(message);
    }
}
