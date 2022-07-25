package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.client.balance.BalanceClientService;
import com.bilyoner.assignment.couponapi.model.enums.TransactionTypeEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class BalanceServiceTest {

    @InjectMocks
    private BalanceService balanceService;

    @Mock
    private BalanceClientService balanceClientService;


    @Test
    public void shouldUpdateBalance(){
        var userId = 1L;
        var amount = BigDecimal.TEN;
        var transactionType = TransactionTypeEnum.REFUND;
        balanceService.updateBalance(userId,amount,transactionType);

        verify(balanceClientService).updateBalance(userId,amount,transactionType);
    }
}
