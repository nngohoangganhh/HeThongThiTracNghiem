package com.hrm.project_spring.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String massage){
    super(massage);
    }
}
