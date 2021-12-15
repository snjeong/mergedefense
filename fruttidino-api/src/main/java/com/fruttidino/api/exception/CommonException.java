package com.fruttidino.api.exception;

public class CommonException extends RuntimeException {

    public static final String I18N_ERROR_UNKNOWN = "i18n.error.unknown";

    public Object[] messageParam;

    public CommonException(){
        this("");
    }

    public CommonException(String message){
        super(message);
    }

    public CommonException(String message, Object[] messageParam){
        super(message);
        this.messageParam = messageParam;
    }

}
