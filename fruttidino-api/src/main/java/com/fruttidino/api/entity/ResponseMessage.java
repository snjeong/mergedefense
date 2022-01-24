package com.fruttidino.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fruttidino.api.common.exception.ErrorCode;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class ResponseMessage {
    public static ResponseMessage ok() {
        return new ResponseMessage(ErrorCode.OK);
    }

    public static ResponseMessage create(ErrorCode errorCode) {
        return new ResponseMessage(errorCode);
    }

    public ResponseMessage(ErrorCode errorCode) {
        this.errorCode = errorCode.getCode();
        this.errorMsg = errorCode.getMessage();
    }

    @JsonProperty("error_code")
    String errorCode;

    @JsonProperty("error_msg")
    String errorMsg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("result")
    Object result;

    public ResponseMessage add(String k, Object v) {

        this.result = new Object();

        ((LinkedHashMap<String, Object>)this.result).put(k, v);

        return this;
    }

    public ResponseMessage set(Object v) {
        this.result = v;

        return this;
    }
}
