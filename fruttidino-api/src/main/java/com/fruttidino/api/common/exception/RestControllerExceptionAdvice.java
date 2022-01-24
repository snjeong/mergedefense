package com.fruttidino.api.common.exception;

import com.fruttidino.api.entity.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * REST Controller 오류처리
 */
@ControllerAdvice
public class RestControllerExceptionAdvice extends AbstractExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(RestControllerExceptionAdvice.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> exceptionHandler(HttpServletRequest req, Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setCode("E999");
        error.setMessage("An error occurred while processing the server. Please contact your administrator.An error occurred while processing the server. Please contact your administrator.");
        error.setDeveloperMessage(ex.getMessage());
        error.setMoreInfo("");

        ex.printStackTrace();
        printError(req, ex, ErrorCode.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(ResponseMessage.create(ErrorCode.INTERNAL_SERVER_ERROR), ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseMessage> handleError404(HttpServletRequest req, Exception ex) {
        printError(req, ex, ErrorCode.BAD_REQUEST);

        return new ResponseEntity<>(ResponseMessage.create(ErrorCode.NOT_FOUND_API), ErrorCode.NOT_FOUND_API.getHttpStatus());
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ResponseMessage> appExceptionHandler(HttpServletRequest req, ApplicationException appEx) {

        String errCd = appEx.getErrorCode().getCode();
        String errMsg = appEx.getMessage();

        ErrorResponse error = new ErrorResponse();

        error.setCode(errCd);
        error.setHttpStatus(appEx.getErrorCode().getHttpStatus());
        error.setStatus(appEx.getErrorCode().getHttpStatus().value());

        if (errMsg != null && !errMsg.isEmpty()) {
            error.setMessage(errMsg);
        }

        // TODO : 개발자 오류 메세지 전달 여부 확인필
        error.setDeveloperMessage(appEx.getMessage());

        printError(req, appEx, appEx.getErrorCode());
        return new ResponseEntity<>(ResponseMessage.create(appEx.getErrorCode()), appEx.getErrorCode().getHttpStatus());
    }

    public void printError(HttpServletRequest req, Exception ex, ErrorCode code) {
/*
        // old
        logger.error("Cause :: " + ex.getCause());
        logger.error("Message :: " + ex.getMessage());
        logger.error("Message :: " + ex.getLocalizedMessage());
        logger.error("ex.toString() :: " + ex.toString());
*/
        // new
        logger.error("ERROR-SOURCE:" + ex.getStackTrace()[0].toString());
        logger.error("REQUEST-ID:" + req.getHeader("REQUEST-ID") + " " + req.getMethod() + " " + req.getRequestURI() +
                " USER-ID:" + req.getHeader("USER-ID") + " [" + ex.toString() + "]" + "[" + ex.getMessage() + "][" + code.getMessage() + "]");
    }

}
