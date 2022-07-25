package com.bilyoner.assignment.couponapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCodeEnum {

    INTERNAL_SERVER_ERROR(1000, "Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR),
    FIELD_VALIDATION_ERROR(1001, "Field validation error.", HttpStatus.BAD_REQUEST),
    CONTENT_NOT_FOUND_ERROR(1002, "Content not found.", HttpStatus.BAD_REQUEST),
    COUPON_CANCEL_ERROR(1003,"Coupon cancellation time exceeded 5 minutes.",HttpStatus.BAD_REQUEST),
    MAX_MBS_ERROR(1004,"The number of bets is not enough for the maximum number of bets.",HttpStatus.BAD_REQUEST),
    BET_TYPE_ERROR(1005,"Cannot to bet on football and tennis matches together.", HttpStatus.BAD_REQUEST),
    STARTED_EVENT_ERROR(1006,"Cannot place bets on events that have started",HttpStatus.BAD_REQUEST),
    PLAYED_COUPON_ERROR(1007,"Cannot play bets on played events.",HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatus httpStatus;
}
