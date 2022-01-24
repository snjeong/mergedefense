package com.fruttidino.api.common.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Description : Error response object (common obj)
 *
    {
     "status": 404,
     "code": 40483,
     "message": "Oops! It looks like that file does not exist.",
     "developerMessage": "File resource for path /uploads/foobar.txt does not exist.Please wait 10 minutes until the upload batch completes before checking again.",
     "moreInfo": "http://www.mycompany.com/errors/40483"
     }
 */
@Data
public class ErrorResponse {

    private HttpStatus httpStatus;
    private int status;
    private String code;
    private String message;
    private String developerMessage;
    private String moreInfo;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String code) {
        this.status = status;
        this.code = code;
    }

    public ErrorResponse(int status, String message, String developerMessage, String moreInfo) {
        this.status = status;
        this.message = message;
        this.developerMessage = developerMessage;
        this.moreInfo = moreInfo;
    }

    public ErrorResponse(int status, String code, String message, String developerMessage, String moreInfo) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.developerMessage = developerMessage;
        this.moreInfo = moreInfo;
    }

}
