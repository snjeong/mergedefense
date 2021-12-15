package com.fruttidino.api.exception;

import lombok.Getter;

public class ExceptionResVo {
    private @Getter String message;

    public static ExceptionResVo create(String message){
        return new ExceptionResVo(message);
    }

    ExceptionResVo(String message){
        this.message = message;
    }
}