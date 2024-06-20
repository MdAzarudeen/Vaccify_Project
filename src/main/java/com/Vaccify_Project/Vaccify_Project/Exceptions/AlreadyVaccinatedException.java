package com.Vaccify_Project.Vaccify_Project.Exceptions;

public class AlreadyVaccinatedException extends RuntimeException{
    public AlreadyVaccinatedException(String msg)
    {
        super(msg);
    }
}
