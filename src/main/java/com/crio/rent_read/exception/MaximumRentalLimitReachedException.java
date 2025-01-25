package com.crio.rent_read.exception;

public class MaximumRentalLimitReachedException extends RuntimeException{

    public MaximumRentalLimitReachedException(String message){
        super(message);
    }
}
