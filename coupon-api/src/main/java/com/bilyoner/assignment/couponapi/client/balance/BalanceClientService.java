package com.bilyoner.assignment.couponapi.client.balance;

import com.bilyoner.assignment.couponapi.model.UpdateBalanceRequest;
import com.bilyoner.assignment.couponapi.model.enums.TransactionTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BalanceClientService {

    private final BalanceClient balanceClient;


    public void updateBalance(Long userId, BigDecimal amount, TransactionTypeEnum transactionTypeEnum){

        final UpdateBalanceRequest updateBalanceRequest = UpdateBalanceRequest.builder()
                .userId(userId)
                .amount(amount)
                .transactionId(UUID.randomUUID().toString())
                .transactionType(transactionTypeEnum)
                .build();
        balanceClient.updateBalance(updateBalanceRequest);
    }
}
