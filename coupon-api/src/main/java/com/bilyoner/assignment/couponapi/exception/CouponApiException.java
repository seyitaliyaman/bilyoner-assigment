package com.bilyoner.assignment.couponapi.exception;

import lombok.Getter;

@Getter
public class CouponApiException extends RuntimeException{

    private final ErrorCodeEnum errorCodeEnum;

    public CouponApiException(ErrorCodeEnum errorCodeEnum){
        super();
        this.errorCodeEnum = errorCodeEnum;
    }
}
