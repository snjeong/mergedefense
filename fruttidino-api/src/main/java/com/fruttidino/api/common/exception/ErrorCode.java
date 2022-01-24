package com.fruttidino.api.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    OK("0", "success", HttpStatus.OK),
    BAD_REQUEST("ERR001", "I18N.NFT.ERR01", HttpStatus.BAD_REQUEST),
    FORBIDDEN("40", "no permission", HttpStatus.UNAUTHORIZED),
    SERVICE_UNAVAILABLE("80", "service temporarily unavailable", HttpStatus.SERVICE_UNAVAILABLE),
    // TRE02=처리중 에러가 발생하였습니다.
    INTERNAL_SERVER_ERROR("ERR002", "I18N.NFT.ERR002", HttpStatus.INTERNAL_SERVER_ERROR),
    // TR999=서버에 오류가 발생하였습니다. 잠시 후 다시 거래하시기 바랍니다.
    UNKNOWN_ERROR("ERR500", "I18N.NFT.ERR500", HttpStatus.INTERNAL_SERVER_ERROR),

    // 입력값처리오류 (HttpStatus.BAD_REQUEST)
    // TPM01=필수 입력항목을 확인해주세요.
    PARAM_ERROR_REQUIRED("PARAM01", "I18N.PARAM01", HttpStatus.BAD_REQUEST),

    // TBW01=주문정보를 찾을 수 없습니다. 주문 TXID를 다시 확인 후 거래하시기 바랍니다.
    NOT_EXIST_CHARACTER("ERR100","not exist nft", HttpStatus.NOT_FOUND);

    private String code;
    private String message;
    private HttpStatus httpStatus;
}

