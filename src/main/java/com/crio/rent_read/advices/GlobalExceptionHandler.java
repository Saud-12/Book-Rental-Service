package com.crio.rent_read.advices;

import com.crio.rent_read.exception.BookAlreadyReturnedException;
import com.crio.rent_read.exception.BookIsNotAvailableForRentException;
import com.crio.rent_read.exception.MaximumRentalLimitReachedException;
import com.crio.rent_read.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError=new ApiError(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

    @ExceptionHandler(MaximumRentalLimitReachedException.class)
    public ResponseEntity<ApiError> handleMaximumRentalLimitReachedException(MaximumRentalLimitReachedException exception){
        ApiError apiError=new ApiError(exception.getMessage(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

    @ExceptionHandler(BookIsNotAvailableForRentException.class)
    public ResponseEntity<ApiError> handleBookIsNotAvailableForRentException(BookIsNotAvailableForRentException exception){
        ApiError apiError=new ApiError(exception.getMessage(),HttpStatus.CONFLICT);
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

    @ExceptionHandler(BookAlreadyReturnedException.class)
    public ResponseEntity<ApiError> handleBookAlreadyReturnedException(BookAlreadyReturnedException exception){
        ApiError apiError=new ApiError(exception.getMessage(),HttpStatus.CONFLICT);
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException exception){
        ApiError apiError=new ApiError(exception.getMessage(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception exception){
        ApiError apiError=new ApiError(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

}
