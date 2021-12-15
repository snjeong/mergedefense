package com.fruttidino.api.exception;

public class BadRequestException extends CommonException {
    public BadRequestException(){
        this("");
    }
    public BadRequestException(String message){
        super(message);
    }
    public BadRequestException(String message, Object[] messageParam){
        super(message, messageParam);
    }
}
