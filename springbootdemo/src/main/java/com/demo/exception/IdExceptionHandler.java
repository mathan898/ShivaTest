package com.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IdExceptionHandler {
	
	@ExceptionHandler
    public ResponseEntity<IdErrorResponse> handleException(IdNotFoundException ine){
        IdErrorResponse errorResponse = new IdErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ine.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<IdErrorResponse>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<IdErrorResponse> handleException(Exception ex){
        IdErrorResponse errorResponse = new IdErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<IdErrorResponse>(errorResponse,HttpStatus.BAD_REQUEST);
    }

}
