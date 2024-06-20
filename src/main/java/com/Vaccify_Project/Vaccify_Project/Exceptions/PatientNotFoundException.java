package com.Vaccify_Project.Vaccify_Project.Exceptions;

public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException (String msg)
    {
        super(msg);
    }
}
