package org.lms.commonmodule.ExceptionHandler.Exceptions.AcademicServiceExceptions;

public class DepartmentNotFoundException extends RuntimeException{
    public DepartmentNotFoundException(String message)
    {
        super(message);
    }
}
