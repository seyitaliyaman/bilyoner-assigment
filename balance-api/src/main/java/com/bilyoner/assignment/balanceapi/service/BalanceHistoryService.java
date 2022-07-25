package com.bilyoner.assignment.balanceapi.service;

import com.bilyoner.assignment.balanceapi.model.UpdateBalanceRequest;
import com.bilyoner.assignment.balanceapi.persistence.entity.UserBalanceHistoryEntity;
import com.bilyoner.assignment.balanceapi.persistence.repository.UserBalanceHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class BalanceHistoryService {

    private final UserBalanceHistoryRepository repository;


    public void createBalanceHistory(UpdateBalanceRequest updateBalanceRequest, BigDecimal oldBalance, BigDecimal newBalance){
        final var userBalanceHistoryEntity = UserBalanceHistoryEntity.builder()
                .userId(updateBalanceRequest.getUserId())
                .oldBalance(oldBalance)
                .newBalance(newBalance)
                .transactionId(updateBalanceRequest.getTransactionId())
                .transactionType(updateBalanceRequest.getTransactionType())
                .build();

        repository.save(userBalanceHistoryEntity);
    }
}
