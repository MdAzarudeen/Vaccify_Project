package com.Vaccify_Project.Vaccify_Project.Exceptions;

public class DoctorAlreadyExistsException extends RuntimeException{
    public DoctorAlreadyExistsException(String msg)
    {
        super(msg);
    }
}
