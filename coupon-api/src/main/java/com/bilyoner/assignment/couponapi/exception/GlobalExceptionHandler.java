package com.bilyoner.assignment.couponapi.exception;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * TODO : Implement all possible exception handlers
     */

    @Value("${spring.application.name}")
    private String serviceName;

    @ExceptionHandler(value = {CouponApiException.class})
    protected ErrorResponse handleCouponApiException(CouponApiException exception){
        return ErrorResponse.builder()
                .code(exception.getErrorCodeEnum().getCode())
                .message(exception.getErrorCodeEnum().getMessage())
                .httpStatus(exception.getErrorCodeEnum().getHttpStatus())
                .serviceName(serviceName)
                .build();
    }

    @ExceptionHandler(value = {Exception.class})
    protected ErrorResponse handleException(Exception exception, HttpServletRequest httpServletRequest){
        log.error(httpServletRequest.getServletPath(), exception);
        return ErrorResponse.builder()
                .code(ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode())
                .message(ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage())
                .httpStatus(ErrorCodeEnum.INTERNAL_SERVER_ERROR.getHttpStatus())
                .serviceName(serviceName)
                .build();
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        final String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .filter(Objects::nonNull)
                .filter(fieldError -> StringUtils.isNotEmpty(fieldError.getField()))
                .map(f -> f.getField().concat(" ").concat("fields are not valid!"))
                .collect(Collectors.joining());

        return ErrorResponse.builder()
                .code(ErrorCodeEnum.FIELD_VALIDATION_ERROR.getCode())
                .message(message)
                .httpStatus(ErrorCodeEnum.FIELD_VALIDATION_ERROR.getHttpStatus())
                .serviceName(serviceName)
                .build();
    }
}