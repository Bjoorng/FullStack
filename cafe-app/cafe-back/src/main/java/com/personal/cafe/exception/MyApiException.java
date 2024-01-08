package com.personal.cafe.exception;

import org.springframework.http.HttpStatus;

public class MyApiException extends RuntimeException {
	
	private HttpStatus status;
    private String message;
    
    public MyApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public MyApiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
    
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
