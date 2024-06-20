package com.Vaccify_Project.Vaccify_Project.Exceptions;

public class VaccinationAddressEmptyException extends RuntimeException
{
    public VaccinationAddressEmptyException(String addressNotFound) {
        super(addressNotFound);
    }

}
