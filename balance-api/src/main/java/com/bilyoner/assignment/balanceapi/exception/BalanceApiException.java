package com.bilyoner.assignment.balanceapi.exception;

import lombok.Getter;

@Getter
public class BalanceApiException extends RuntimeException {

    private final ErrorCodeEnum errorCodeEnum;

    public BalanceApiException(ErrorCodeEnum errorCodeEnum) {
        super();
        this.errorCodeEnum = errorCodeEnum;
    }
}
