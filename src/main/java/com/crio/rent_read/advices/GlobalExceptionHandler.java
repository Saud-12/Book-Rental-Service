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
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError=new ApiError(exception.getMessage(), HttpStatus.NOT_FOUND);
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(MaximumRentalLimitReachedException.class)
    public ResponseEntity<ApiResponse<?>> handleMaximumRentalLimitReachedException(MaximumRentalLimitReachedException exception){
        ApiError apiError=new ApiError(exception.getMessage(),HttpStatus.BAD_REQUEST);
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(BookIsNotAvailableForRentException.class)
    public ResponseEntity<ApiResponse<?>> handleBookIsNotAvailableForRentException(BookIsNotAvailableForRentException exception){
        ApiError apiError=new ApiError(exception.getMessage(),HttpStatus.CONFLICT);
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(BookAlreadyReturnedException.class)
    public ResponseEntity<ApiResponse<?>> handleBookAlreadyReturnedException(BookAlreadyReturnedException exception){
        ApiError apiError=new ApiError(exception.getMessage(),HttpStatus.CONFLICT);
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>>  handleAccessDeniedException(AccessDeniedException exception){
        ApiError apiError=new ApiError(exception.getMessage(), HttpStatus.FORBIDDEN);
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception exception){
        ApiError apiError=new ApiError(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return buildErrorResponseEntity(apiError);
    }
    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError),apiError.getHttpStatus());
    }

}
