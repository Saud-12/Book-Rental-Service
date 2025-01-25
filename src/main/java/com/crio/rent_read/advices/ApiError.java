package com.crio.rent_read.advices;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiError {
    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime localDateTime;

    public ApiError(){
        this.localDateTime=LocalDateTime.now();
    }

    public ApiError(String message,HttpStatus httpStatus){
        this();
        this.message=message;
        this.httpStatus=httpStatus;
    }
}
