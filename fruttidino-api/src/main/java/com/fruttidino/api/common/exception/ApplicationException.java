package com.fruttidino.api.common.exception;

import lombok.Getter;

/**
 * 오류처리 객체
 */
@Getter
public class ApplicationException extends Exception {

    private ErrorCode errorCode;

    public ApplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}