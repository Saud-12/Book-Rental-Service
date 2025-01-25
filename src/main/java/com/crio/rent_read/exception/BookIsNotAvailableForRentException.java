package com.crio.rent_read.exception;

public class BookIsNotAvailableForRentException extends RuntimeException{
    public BookIsNotAvailableForRentException(String message){
        super(message);
    }
}
