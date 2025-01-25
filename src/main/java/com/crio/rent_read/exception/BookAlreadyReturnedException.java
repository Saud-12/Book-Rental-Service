package com.crio.rent_read.exception;

public class BookAlreadyReturnedException extends RuntimeException{
    public BookAlreadyReturnedException(String message){
        super(message);
    }
}
