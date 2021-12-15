package com.fruttidino.api.exception;

public class ApplicationException extends CommonException {
    public ApplicationException(){
        this("");
    }
    public ApplicationException(String message){
        super(message);
    }
    public ApplicationException(String message, Object[] messageParam){
        super(message, messageParam);
    }
}
