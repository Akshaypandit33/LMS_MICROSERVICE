package org.lms.commonmodule.ExceptionHandler.Exceptions.UserServiceExceptions;

public class UserRoleAssignmentException extends RuntimeException{
    public UserRoleAssignmentException(String message)
    {
        super(message);
    }
}
