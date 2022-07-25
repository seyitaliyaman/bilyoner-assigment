package com.bilyoner.assignment.balanceapi.validator;

import com.bilyoner.assignment.balanceapi.exception.BalanceApiException;
import com.bilyoner.assignment.balanceapi.exception.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class BalanceValidator {

    public void validateBalance(BigDecimal userBalanceAmount, BigDecimal couponPrice){
        if (userBalanceAmount.compareTo(couponPrice) < 0)
            throw new BalanceApiException(ErrorCodeEnum.INSUFFICIENT_BALANCE);
    }
}
