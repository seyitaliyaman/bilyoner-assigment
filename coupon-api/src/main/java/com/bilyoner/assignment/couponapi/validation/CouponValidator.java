package com.bilyoner.assignment.couponapi.validation;

import com.bilyoner.assignment.couponapi.exception.CouponApiException;
import com.bilyoner.assignment.couponapi.exception.ErrorCodeEnum;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.persistence.entity.CouponEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CouponValidator {

    public void validateCouponIsCancelable(CouponEntity coupon){
        if(LocalDateTime.now().isAfter(coupon.getPlayDate().plusMinutes(5)))
            throw new CouponApiException(ErrorCodeEnum.COUPON_CANCEL_ERROR);
    }

    public void validateCouponIsPlayed(CouponEntity coupon){
        if (coupon.getStatus().equals(CouponStatusEnum.PLAYED))
            throw new CouponApiException(ErrorCodeEnum.PLAYED_COUPON_ERROR);
    }

}
