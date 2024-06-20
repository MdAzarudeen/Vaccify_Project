package com.Vaccify_Project.Vaccify_Project.Exceptions;

public class AppointmentAlreadyDoneException extends RuntimeException{
    public AppointmentAlreadyDoneException (String msg)
    {
        super(msg);
    }
}
