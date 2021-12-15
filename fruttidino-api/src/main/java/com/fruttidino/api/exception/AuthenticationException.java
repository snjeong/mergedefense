package com.fruttidino.api.exception;

public class AuthenticationException extends CommonException {
    public AuthenticationException(){
        this("");
    }
    public AuthenticationException(String message){
        super(message);
    }
    public AuthenticationException(String message, Object[] messageParam){
        super(message, messageParam);
    }
}
