package com.example.healthcheckapp.exception;

/**
 * Created By MMT6540 on 09 Apr, 2018
 */
public class UnauthorizedException extends ApplicationException{

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}